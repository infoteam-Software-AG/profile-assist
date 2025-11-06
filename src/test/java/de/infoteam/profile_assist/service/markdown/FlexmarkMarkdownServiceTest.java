package de.infoteam.profile_assist.service.markdown;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FlexmarkMarkdownServiceTest {
  private final MarkdownService md = new FlexmarkMarkdownService();

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
  @Test
  void renderToHtml_nullInput_returnsEmptyHtml() {
    assertThat(md.renderToHtml(null)).isEmpty();
  }

  @DisplayName("Check if an valid String gets converted into a proper HTML")
  @Test
  void renderToHtml_nonNull_returnsConvertedHtml() {
    assertThat(md.renderToHtml("**b**")).contains("<strong>b</strong>");
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
      title: [bonk
      ---
      content
      """;

    assertThatThrownBy(() -> md.extractFrontMatter(mdText))
        .isInstanceOf(FrontMatterParseException.class)
        .hasMessageContaining("Invalid YAML front matter");
  }
}
