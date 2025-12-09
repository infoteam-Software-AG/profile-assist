// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.common.markdown;

import de.infoteam.profile_assist.common.markdown.mapping.NodeMappingFactory;
import java.util.List;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.markdown.MarkdownRenderer;
import org.springframework.stereotype.Component;

@Component
public class CommonmarkMarkdownMapper implements MarkdownMapper {

  private final Parser parser;

  private final MarkdownRenderer renderer;

  private final NodeMappingFactory nodeMappingFactory;

  public CommonmarkMarkdownMapper(
      Parser parser, MarkdownRenderer renderer, NodeMappingFactory nodeMappingFactory) {
    this.parser = parser;
    this.renderer = renderer;
    this.nodeMappingFactory = nodeMappingFactory;
  }

  @Override
  public Document toAst(String markdown) {
    if (markdown == null) {
      return new Document(List.of());
    }
    Node node = parser.parse(markdown);
    return (Document) nodeMappingFactory.mapNode(node);
  }

  @Override
  public String toMarkdown(Document ast) {
    if (ast == null) {
      return "";
    }
    var doc = nodeMappingFactory.mapNode(ast);
    return renderer.render(doc);
  }
}
