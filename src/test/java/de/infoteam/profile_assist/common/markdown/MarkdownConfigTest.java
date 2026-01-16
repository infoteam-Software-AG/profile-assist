package de.infoteam.profile_assist.common.markdown;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.markdown.MarkdownRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownConfigTest {

  private MarkdownConfig config;

  @BeforeEach
  void setUp() {
    config = new MarkdownConfig();
  }

  @Test
  void commonmarkParser() {
    Parser parser = config.commonmarkParser();
    assertNotNull(parser);
  }

  @Test
  void commonmarkRenderer() {
    MarkdownRenderer markdownRenderer = config.commonmarkRenderer();
    assertNotNull(markdownRenderer);
  }
}