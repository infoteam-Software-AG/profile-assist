package de.infoteam.profile_assist.common.markdown;

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

  @DisplayName("Check if a valid input gets converted into a sanitized HTML")
  @ParameterizedTest(name = "Case {index}: input={0}")
  @MethodSource("markdownToHtmlPositiveCases")
  void renderToHtml_validInput_returnsSanitizedHtml(String markdown, String expectedHtml) {
    String html = md.renderToHtml(markdown);
    assertThat(html).contains(expectedHtml);
  }

  @DisplayName("Check if forbidden input gets removed from converted and sanitized HTML")
  @ParameterizedTest(name = "Case {index}: input={0}")
  @MethodSource("markdownToHtmlNegativeCases")
  void renderToHtml_forbiddenInput_returnsSanitizedHtml(String markdown, String forbiddenHtml) {
    String html = md.renderToHtml(markdown);
    assertThat(html).doesNotContain(forbiddenHtml);
  }

  @DisplayName("Check if null as input returns an empty HTML")
  @NullAndEmptySource
  @ValueSource(strings = {" ", ""})
  @ParameterizedTest
  void renderToHtml_invalidInput_returnsEmptyHTML(String input) {
    assertThat(md.renderToHtml(input)).isEmpty();
  }

  @DisplayName(
      "Check if metadata header is correctly mapped by checking if title and production exists")
  @Test
  void extractMetadataHeader_returnsMap() {
    var fm =
        md.extractMetadataHeader(
            """
                                ---
                                title: X
                                production: false
                                ---
                                Content
                                """);
    assertThat(fm).containsEntry("title", "X").containsEntry("production", false);
  }

  @DisplayName("Check if metadata header is an empty map when sending no YAML")
  @Test
  void extractMetadataHeader_noYaml_returnsEmpty() {
    assertThat(md.extractMetadataHeader("No YAML")).isEmpty();
  }

  @DisplayName("Check if an invalid YAML throws an error")
  @Test
  void extractMetadataHeader_invalidYaml_throwsError() {
    String mdText =
        """
      ---
      title: [megabonk
      ---
      content
      """;

    assertThatThrownBy(() -> md.extractMetadataHeader(mdText))
        .isInstanceOf(MetadataHeaderParseException.class)
        .hasMessageContaining("Invalid YAML metadata header");
  }

  @DisplayName("Check at the start of extraction if markdown null or blank returns an empty map")
  @NullAndEmptySource
  @ValueSource(strings = {" ", ""})
  @ParameterizedTest(name = "Case {index}: input={0} → empty map")
  void extractMetadataHeader_nullOrBlank_returnsEmpty(String input) {
    assertThat(md.extractMetadataHeader(input)).isEmpty();
  }

  public static Stream<Arguments> markdownToHtmlNegativeCases() {
    return Stream.of(
        Arguments.of("<script>alert('x')</script>", "<script>"),
        Arguments.of("[Click me](javascript:alert('XSS!'))", "javascript:"),
        Arguments.of("~~strike~~", "<del>"),
        Arguments.of("- [x] done", "<input"),
        Arguments.of(
            """
          | A | B |
          |---|---|
          | A1| B1|
          """,
            "<table>"),
        Arguments.of("Bare URL: https://example.com", "<a href=\"https://example.com\""),
        Arguments.of("<img src='x' onerror='alert(1)'>", "onerror"));
  }

  public static Stream<Arguments> markdownToHtmlPositiveCases() {
    return Stream.of(
        Arguments.of("**b**", "<strong>b</strong>"),
        Arguments.of("*i*", "<em>i</em>"),
        Arguments.of("# Title", "<h1>Title</h1>"),
        Arguments.of("`x`", "<code>x</code>"),
        Arguments.of("- item", "<li>item</li>"),
        Arguments.of("> quote", "<blockquote>"),
        Arguments.of("[doc](https://example.com)", "<a href=\"https://example.com\""),
        Arguments.of(
            "See <https://example.com/docs?ref=md#sec>",
            "<a href=\"https://example.com/docs?ref=md#sec\""),
        Arguments.of(
            """
          ```java
          class A {}
          ```
          """,
            "<pre><code"));
  }
}
