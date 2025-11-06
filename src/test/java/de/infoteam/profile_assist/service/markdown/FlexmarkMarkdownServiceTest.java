package de.infoteam.profile_assist.service.markdown;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class FlexmarkMarkdownServiceTest {
  private final MarkdownService md = new FlexmarkMarkdownService();

  public static Stream<Arguments> markdownToHtmlCases() {
    return Stream.of(
        Arguments.of("bold", "**b**", "<strong>b</strong>"),
        Arguments.of("italic", "*i*", "<em>i</em>"),
        Arguments.of("strike", "~~s~~", "<del>s</del>"),
        Arguments.of("heading", "# Title", "<h1>Title</h1>"),
        Arguments.of("code", "`x`", "<code>x</code>"));
  }

  @DisplayName("Check if markdown String is properly converted to HTML and sanitized")
  @Test
  void renderToHTML_isConvertedAndSanitized() {
    String in =
        """
            # Title

            **bold** *italic* ~~strike~~

            - [x] done
            - [ ] open

            | A | B | C |
            |---|---|---|
            | A1| B1| C1|
            | A2| B2|

            Plain URL: <https://example.com/docs?ref=markdown#section>

            ```java
            class A {}
            ```

            <script>alert('x')</script>
            """;

    String html = md.renderToHtml(in);
    assertThat(html)
        // renderer features
        .contains("<h1>Title</h1>", "<strong>bold</strong>", "<em>italic</em>", "<del>strike</del>")
        .contains("<table>", "</table>", "<td></td>")
        .contains("<pre><code", "</code></pre>")
        // task list
        .contains("<input", "type=\"checkbox\"")
        // autolink
        .contains("<a href=\"https://example.com/docs?ref=markdown#section\"")
        // script checking
        .doesNotContain("<script>", "javascript:");
  }

  @DisplayName("Check if null as input returns an empty HTML")
  @NullAndEmptySource
  @ValueSource(strings = {" ", ""})
  @ParameterizedTest
  void renderToHtml_invalidInput_returnsEmptyHTML(String input) {
    assertThat(md.renderToHtml(input)).isEmpty();
  }

  @DisplayName("Check if a valid input gets converted into a proper HTML")
  @ParameterizedTest(name = "Case {index}: input={0}")
  @MethodSource("markdownToHtmlCases")
  void renderToHtml_validInput_returnsConvertedHtml(
      String caseName, String markdown, String expectedHtml) {
    String html = md.renderToHtml(markdown);
    assertThat(html).contains(expectedHtml);
  }

  @DisplayName("Check if front matter is correctly mapped by checking if title and draft exists")
  @Test
  void extractFrontMatter_returnsMap() {
    var fm =
        md.extractFrontMatter(
            """
                                ---
                                title: X
                                draft: false
                                ---
                                Content
                                """);
    assertThat(fm).containsEntry("title", "X").containsEntry("draft", false);
  }

  @DisplayName("Check if front matter is an empty map when sending no YAML")
  @Test
  void extractFrontMatter_noYaml_returnsEmpty() {
    assertThat(md.extractFrontMatter("No YAML")).isEmpty();
  }

  @DisplayName("Check if an invalid YAML throws an error")
  @Test
  void extractFrontMatter_invalidYaml_throwsError() {
    String mdText =
        """
      ---
      title: [megabonk
      ---
      content
      """;

    assertThatThrownBy(() -> md.extractFrontMatter(mdText))
        .isInstanceOf(FrontMatterParseException.class)
        .hasMessageContaining("Invalid YAML front matter");
  }

  @DisplayName("Check at start of extraction if markdown null or blank return an empty map")
  @NullAndEmptySource
  @ValueSource(strings = {" ", ""})
  @ParameterizedTest(name = "Case {index}: input={0} → empty map")
  void extractFrontMatter_nullOrBlank_returnsEmpty(String input) {
    assertThat(md.extractFrontMatter(input)).isEmpty();
  }
}
