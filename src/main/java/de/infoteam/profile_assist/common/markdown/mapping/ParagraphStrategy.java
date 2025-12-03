/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import de.infoteam.profile_assist.common.markdown.Paragraph;
import java.util.ArrayList;
import java.util.List;
import org.commonmark.node.Node;
import org.springframework.stereotype.Component;

@Component
public class ParagraphStrategy implements MappingStrategy {

  @Override
  public MarkdownNode map(Node node, NodeMappingFactory nodeMappingFactory) {
    List<MarkdownNode> children = new ArrayList<>();
    for (var child = node.getFirstChild(); child != null; child = child.getNext()) {
      children.add(nodeMappingFactory.mapNode(child));
    }
    return new Paragraph(children);
  }

  @Override
  public Node map(MarkdownNode node, NodeMappingFactory nodeMappingFactory) {
    var paragraph = new org.commonmark.node.Paragraph();
    for (MarkdownNode child : ((Paragraph) node).children()) {
      paragraph.appendChild(nodeMappingFactory.mapNode(child));
    }
    return paragraph;
  }

  @Override
  public boolean canMap(Node node) {
    return node instanceof org.commonmark.node.Paragraph;
  }

  @Override
  public boolean canMap(MarkdownNode markdownNode) {
    return markdownNode instanceof Paragraph;
  }
}
