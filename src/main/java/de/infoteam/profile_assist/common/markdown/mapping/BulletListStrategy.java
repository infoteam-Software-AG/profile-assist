/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.BulletList;
import de.infoteam.profile_assist.common.markdown.ListItem;
import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import java.util.ArrayList;
import java.util.List;
import org.commonmark.node.Node;
import org.springframework.stereotype.Component;

@Component
public class BulletListStrategy implements MappingStrategy {

  @Override
  public MarkdownNode map(Node node, NodeMappingFactory nodeMappingFactory) {
    List<ListItem> items = new ArrayList<>();
    for (var child = node.getFirstChild(); child != null; child = child.getNext()) {
      if (child instanceof org.commonmark.node.ListItem childNode) {
        items.add((ListItem) nodeMappingFactory.mapNode(childNode));
      }
    }
    return new BulletList(items);
  }

  @Override
  public Node map(MarkdownNode node, NodeMappingFactory nodeMappingFactory) {
    var bulletList = new org.commonmark.node.BulletList();
    bulletList.setTight(true);
    for (ListItem child : ((BulletList) node).items()) {
      bulletList.appendChild(nodeMappingFactory.mapNode(child));
    }
    return bulletList;
  }

  @Override
  public boolean canMap(Node node) {
    return node instanceof org.commonmark.node.BulletList;
  }

  @Override
  public boolean canMap(MarkdownNode markdownNode) {
    return markdownNode instanceof BulletList;
  }
}
