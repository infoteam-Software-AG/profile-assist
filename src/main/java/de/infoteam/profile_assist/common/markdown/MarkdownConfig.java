/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.common.markdown;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.markdown.MarkdownRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MarkdownConfig {
  // AST output
  @Bean
  Parser commonmarkParser() {
    return Parser.builder().build();
  }

  // markdown output
  @Bean
  MarkdownRenderer commonmarkRenderer() {
    return MarkdownRenderer.builder().build();
  }
}
