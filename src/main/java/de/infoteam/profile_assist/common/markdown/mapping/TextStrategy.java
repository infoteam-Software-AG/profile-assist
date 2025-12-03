/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import de.infoteam.profile_assist.common.markdown.Text;
import org.commonmark.node.Node;
import org.springframework.stereotype.Component;

@Component
public class TextStrategy implements MappingStrategy {

  @Override
  public MarkdownNode map(Node node, NodeMappingFactory nodeMappingFactory) {
    return new Text(((org.commonmark.node.Text) node).getLiteral());
  }

  @Override
  public Node map(MarkdownNode node, NodeMappingFactory nodeMappingFactory) {
    String resultText = ((Text) node).text();
    return new org.commonmark.node.Text(resultText);
  }

  @Override
  public boolean canMap(Node node) {
    return node instanceof org.commonmark.node.Text;
  }

  @Override
  public boolean canMap(MarkdownNode markdownNode) {
    return markdownNode instanceof Text;
  }
}
