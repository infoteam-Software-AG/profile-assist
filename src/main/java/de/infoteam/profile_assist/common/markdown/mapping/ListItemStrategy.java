package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.ListItem;
import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import java.util.ArrayList;
import java.util.List;
import org.commonmark.node.Node;

public class ListItemStrategy implements MappingStrategy {

  @Override
  public MarkdownNode mapToAST(Node node) {

    List<MarkdownNode> children = new ArrayList<>();
    for (var child = node.getFirstChild(); child != null; child = child.getNext()) {
      children.add(MappingStrategyFactory.executeStrategy(child));
    }
    return new ListItem(children);
  }

  @Override
  public Node mapToCommonMark(MarkdownNode node) {
    var resultListItem = new org.commonmark.node.ListItem();
    var listItemParagraph = new org.commonmark.node.Paragraph();
    for (MarkdownNode child : ((ListItem) node).children()) {
      Node commonmarkChild = MappingStrategyFactory.executeStrategy(child);
      listItemParagraph.appendChild(commonmarkChild);
    }
    resultListItem.appendChild(listItemParagraph);
    return resultListItem;
  }
}
