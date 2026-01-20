// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.common.markdown;

import static org.assertj.core.api.Assertions.assertThat;

import de.infoteam.profile_assist.common.markdown.mapping.BulletListStrategy;
import de.infoteam.profile_assist.common.markdown.mapping.DocumentStrategy;
import de.infoteam.profile_assist.common.markdown.mapping.HeadingStrategy;
import de.infoteam.profile_assist.common.markdown.mapping.ListItemStrategy;
import de.infoteam.profile_assist.common.markdown.mapping.NodeMappingFactory;
import de.infoteam.profile_assist.common.markdown.mapping.ParagraphStrategy;
import de.infoteam.profile_assist.common.markdown.mapping.StrongEmphasisStrategy;
import de.infoteam.profile_assist.common.markdown.mapping.TextStrategy;
import java.util.List;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.markdown.MarkdownRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("CommonmarkMarkdownMapperTest")
class CommonmarkMarkdownMapperTest {

  private CommonmarkMarkdownMapper mapper;

  @BeforeEach
  void setUp() {
    // Given (common)
    Parser parser = Parser.builder().build();
    MarkdownRenderer renderer = MarkdownRenderer.builder().build();
    mapper =
        new CommonmarkMarkdownMapper(
            parser,
            renderer,
            new NodeMappingFactory(
                List.of(
                    new HeadingStrategy(),
                    new BulletListStrategy(),
                    new ParagraphStrategy(),
                    new ListItemStrategy(),
                    new DocumentStrategy(),
                    new ParagraphStrategy(),
                    new StrongEmphasisStrategy(),
                    new TextStrategy())));
  }

  @DisplayName("When markdown is null or empty, then Document has no children")
  @ParameterizedTest(name = "Case {index}: markdown={0}")
  @NullAndEmptySource
  void whenMarkdownIsNullOrEmpty_thenDocumentIsEmpty(String markdown) {
    // When
    Document document = mapper.toAst(markdown);

    // Then
    assertThat(document).as("Document for null/empty markdown must not be null").isNotNull();
    assertThat(document.children())
        .as("Document for null/empty markdown must have no children")
        .isEmpty();
  }

  @DisplayName(
      "When markdown contains heading and paragraph, then AST contains Heading and Paragraph nodes")
  @Test
  void whenMarkdownContainsHeadingAndParagraph_thenAstContainsHeadingAndParagraph() {
    // Given
    String markdown =
        """
        # Title

        Some text.
        """;

    // When
    Document document = mapper.toAst(markdown);

    // Then
    assertThat(document.children()).hasSize(2);

    // Heading
    Heading heading = (Heading) document.children().getFirst();
    assertThat(heading).isInstanceOf(Heading.class);
    assertThat(heading.level()).isEqualTo(1);
    assertThat(heading.children()).singleElement().isInstanceOf(Text.class);
    assertThat(((Text) heading.children().getFirst()).text()).isEqualTo("Title");

    // Paragraph
    Paragraph paragraph = (Paragraph) document.children().get(1);
    assertThat(paragraph).isInstanceOf(Paragraph.class);
    assertThat(paragraph.children()).singleElement().isInstanceOf(Text.class);
    assertThat(((Text) paragraph.children().getFirst()).text()).isEqualTo("Some text.");
  }

  @DisplayName("When markdown contains a bullet list, then AST contains BulletList with ListItems")
  @Test
  void whenMarkdownContainsBulletList_thenAstContainsBulletListAndItems() {
    // Given
    String markdown =
        """
        - first item
        - second item
        """;

    // When
    Document document = mapper.toAst(markdown);

    // Then
    assertThat(document.children()).hasSize(1).first().isInstanceOf(BulletList.class);

    BulletList list = (BulletList) document.children().getFirst();
    assertThat(list.items()).hasSize(2);

    // First ListItem
    ListItem firstItem = list.items().getFirst();
    assertThat(firstItem.children()).first().isInstanceOf(Paragraph.class);
    Paragraph firstParagraph = (Paragraph) firstItem.children().getFirst();
    assertThat(firstParagraph.children()).singleElement().isInstanceOf(Text.class);
    assertThat(((Text) firstParagraph.children().getFirst()).text()).isEqualTo("first item");

    // Second ListItem
    ListItem secondItem = list.items().get(1);
    assertThat(secondItem.children()).first().isInstanceOf(Paragraph.class);
    Paragraph secondParagraph = (Paragraph) secondItem.children().getFirst();
    assertThat(secondParagraph.children()).singleElement().isInstanceOf(Text.class);
    assertThat(((Text) secondParagraph.children().getFirst()).text()).isEqualTo("second item");
  }

  @DisplayName("When markdown is mapped to AST and back, then content and structure are preserved")
  @Test
  void whenMarkdownRoundTripsThroughAst_thenContentIsPreserved() {
    // Given
    String markdown =
        """
        # Title

        Some **bold** text.

        - first item
        - second item
        """;

    // When
    Document ast = mapper.toAst(markdown);
    String result = mapper.toMarkdown(ast);

    // Then
    String expected =
        """
        # Title

        Some **bold** text.

        - first item
        - second item
        """;
    assertThat(result).isEqualTo(expected);
  }

  @DisplayName("When empty AST is mapped to Markdown, then empty String preserved")
  @Test
  void whenEmptyAstIsMappedToMarkdown_thenEmptyStringIsPreserved() {
    // Given

    // When
    String result = mapper.toMarkdown(null);

    // Then
    String expected = "";
    assertThat(result).isEqualTo(expected);
  }

  @DisplayName(
      "When document is created manually and mapped to markdown and back, then structure stays intact")
  @Test
  void whenDocumentRoundTripsThroughMarkdown_thenStructureIsPreserved() {
    // Given
    Document newDocument =
        MarkdownBuilder.document()
            .addHeading(1, "Title")
            .addParagraph("Some text.")
            .addBulletList()
            .addListItem("first item")
            .addListItem("second item")
            .endBulletList()
            .build();

    // When
    String markdown = mapper.toMarkdown(newDocument);
    Document parsedDocument = mapper.toAst(markdown);

    // Then
    assertThat(parsedDocument.children()).hasSize(3);

    // Heading
    Heading heading = (Heading) parsedDocument.children().getFirst();
    assertThat(heading).isInstanceOf(Heading.class);
    assertThat(heading.level()).isEqualTo(1);
    assertThat(((Text) heading.children().getFirst()).text()).isEqualTo("Title");

    // Paragraph
    Paragraph paragraph = (Paragraph) parsedDocument.children().get(1);
    assertThat(paragraph).isInstanceOf(Paragraph.class);
    assertThat(((Text) paragraph.children().getFirst()).text()).isEqualTo("Some text.");

    // Bullet list
    BulletList list = (BulletList) parsedDocument.children().get(2);
    assertThat(list).isInstanceOf(BulletList.class);
    assertThat(list.items()).hasSize(2);
    assertThat(
            ((Text) ((Paragraph) list.items().get(0).children().getFirst()).children().getFirst())
                .text())
        .isEqualTo("first item");
    assertThat(
            ((Text) ((Paragraph) list.items().get(1).children().getFirst()).children().getFirst())
                .text())
        .isEqualTo("second item");
  }

  @Test
  void roundtripWithBoldInline_preservesStructure() {
    String md = "Some **bold** text.";

    Document ast = mapper.toAst(md);
    String result = mapper.toMarkdown(ast);

    assertThat(result).contains("**bold**");
  }

  @Test
  void buildsBulletList_includingParagraphWithStrongInline() {
    // Given
    Document doc =
        MarkdownBuilder.document()
            .addBulletList()
            .addListItem(in -> in.text("Some ").strong("bold").text(" text."))
            .endBulletList()
            .addParagraph(in -> in.text("Some more ").strong("bold").text(" text."))
            .build();

    // When
    BulletList bulletList = (BulletList) doc.children().get(0);
    Paragraph bulletListParagraph = (Paragraph) bulletList.items().getFirst().children().getFirst();
    Paragraph paragraph = (Paragraph) doc.children().get(1);

    // Then
    assertThat(paragraph.children()).hasSize(3);
    assertThat(paragraph.children().get(1)).isInstanceOf(StrongEmphasis.class);

    assertThat(bulletListParagraph.children().get(1)).isInstanceOf(StrongEmphasis.class);
  }
}
