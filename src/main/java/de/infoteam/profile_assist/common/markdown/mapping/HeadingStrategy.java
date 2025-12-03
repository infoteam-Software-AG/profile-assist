/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.Heading;
import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import java.util.ArrayList;
import java.util.List;
import org.commonmark.node.Node;
import org.springframework.stereotype.Component;

@Component
public class HeadingStrategy implements MappingStrategy {

  @Override
  public MarkdownNode map(Node node, NodeMappingFactory nodeMappingFactory) {
    int headingLevel = ((org.commonmark.node.Heading) node).getLevel();
    List<MarkdownNode> children = new ArrayList<>();
    for (var child = node.getFirstChild(); child != null; child = child.getNext()) {
      children.add(nodeMappingFactory.mapNode(child));
    }

    return new Heading(headingLevel, children);
  }

  @Override
  public Node map(MarkdownNode node, NodeMappingFactory nodeMappingFactory) {
    var resultHeading = new org.commonmark.node.Heading();

    resultHeading.setLevel(((Heading) node).level());

    for (MarkdownNode child : (((Heading) node).children())) {
      resultHeading.appendChild(nodeMappingFactory.mapNode(child));
    }
    return resultHeading;
  }

  @Override
  public boolean canMap(Node node) {
    return node instanceof org.commonmark.node.Heading;
  }

  @Override
  public boolean canMap(MarkdownNode markdownNode) {
    return markdownNode instanceof Heading;
  }
}
