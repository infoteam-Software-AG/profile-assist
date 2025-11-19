package de.infoteam.profile_assist.common.markdown;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.markdown.MarkdownRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FlexmarkMarkdownMapperTest")
class CommonmarkMarkdownMapperTest {

  private CommonmarkMarkdownMapper mapper;

  @BeforeEach
  void setUp() {
    // Given (common)
    Parser parser = Parser.builder().build();
    MarkdownRenderer renderer = MarkdownRenderer.builder().build();
    mapper = new CommonmarkMarkdownMapper(parser, renderer);
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
    Heading heading = (Heading) document.children().get(0);
    assertThat(heading).isInstanceOf(Heading.class);
    assertThat(heading.level()).isEqualTo(1);
    assertThat(heading.children()).singleElement().isInstanceOf(Text.class);
    assertThat(((Text) heading.children().get(0)).text()).isEqualTo("Title");

    // Paragraph
    Paragraph paragraph = (Paragraph) document.children().get(1);
    assertThat(paragraph).isInstanceOf(Paragraph.class);
    assertThat(paragraph.children()).singleElement().isInstanceOf(Text.class);
    assertThat(((Text) paragraph.children().get(0)).text()).isEqualTo("Some text.");
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

    BulletList list = (BulletList) document.children().get(0);
    assertThat(list.items()).hasSize(2);

    // First ListItem
    ListItem firstItem = list.items().get(0);
    assertThat(firstItem.children()).first().isInstanceOf(Paragraph.class);
    Paragraph firstParagraph = (Paragraph) firstItem.children().get(0);
    assertThat(firstParagraph.children()).singleElement().isInstanceOf(Text.class);
    assertThat(((Text) firstParagraph.children().get(0)).text()).isEqualTo("first item");

    // Second ListItem
    ListItem secondItem = list.items().get(1);
    assertThat(secondItem.children()).first().isInstanceOf(Paragraph.class);
    Paragraph secondParagraph = (Paragraph) secondItem.children().get(0);
    assertThat(secondParagraph.children()).singleElement().isInstanceOf(Text.class);
    assertThat(((Text) secondParagraph.children().get(0)).text()).isEqualTo("second item");
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

  @DisplayName(
      "When AST is created manually and mapped to markdown and back, then structure stays intact")
  @Test
  void whenAstRoundTripsThroughMarkdown_thenStructureIsPreserved() {
    // Given
    Document originalAst =
        new Document(
            List.of(
                new Heading(1, List.of(new Text("Title"))),
                new Paragraph(List.of(new Text("Some text."))),
                new BulletList(
                    List.of(
                        new ListItem(List.of(new Paragraph(List.of(new Text("first item"))))),
                        new ListItem(List.of(new Paragraph(List.of(new Text("second item")))))))));

    // When
    String markdown = mapper.toMarkdown(originalAst);
    Document parsedAst = mapper.toAst(markdown);

    // Then
    assertThat(parsedAst.children()).hasSize(3);

    // Heading
    Heading heading = (Heading) parsedAst.children().get(0);
    assertThat(heading).isInstanceOf(Heading.class);
    assertThat(heading.level()).isEqualTo(1);
    assertThat(((Text) heading.children().get(0)).text()).isEqualTo("Title");

    // Paragraph
    Paragraph paragraph = (Paragraph) parsedAst.children().get(1);
    assertThat(paragraph).isInstanceOf(Paragraph.class);
    assertThat(((Text) paragraph.children().get(0)).text()).isEqualTo("Some text.");

    // Bullet list
    BulletList list = (BulletList) parsedAst.children().get(2);
    assertThat(list).isInstanceOf(BulletList.class);
    assertThat(list.items()).hasSize(2);
    assertThat(
            ((Text) ((Paragraph) list.items().get(0).children().get(0)).children().get(0)).text())
        .isEqualTo("first item");
    assertThat(
            ((Text) ((Paragraph) list.items().get(1).children().get(0)).children().get(0)).text())
        .isEqualTo("second item");
  }
}
