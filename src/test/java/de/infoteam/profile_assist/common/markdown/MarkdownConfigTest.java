// SPDX-FileCopyrightText: 2026 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.common.markdown;

import static org.junit.Assert.assertNotNull;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.markdown.MarkdownRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
