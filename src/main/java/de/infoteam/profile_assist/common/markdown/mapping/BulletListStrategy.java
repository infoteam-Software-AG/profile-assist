package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.BulletList;
import de.infoteam.profile_assist.common.markdown.ListItem;
import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import java.util.ArrayList;
import java.util.List;
import org.commonmark.node.Node;

public class BulletListStrategy implements MappingStrategy {

  @Override
  public MarkdownNode mapToAST(Node node) {
    List<ListItem> items = new ArrayList<>();
    for (var child = node.getFirstChild(); child != null; child = child.getNext()) {
      if (child instanceof org.commonmark.node.ListItem childNode) {
        items.add((ListItem) MappingStrategyFactory.executeStrategy(childNode));
      }
    }
    return new BulletList(items);
  }

  @Override
  public Node mapToCommonMark(MarkdownNode node) {
    var bulletList = new org.commonmark.node.BulletList();
    bulletList.setTight(true);
    for (ListItem child : ((BulletList) node).items()) {
      bulletList.appendChild(MappingStrategyFactory.executeStrategy(child));
    }
    return bulletList;
  }
}
