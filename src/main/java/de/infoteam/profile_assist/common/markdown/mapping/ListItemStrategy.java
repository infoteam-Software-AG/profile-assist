// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.ListItem;
import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import java.util.ArrayList;
import java.util.List;
import org.commonmark.node.Node;
import org.springframework.stereotype.Component;

@Component
public class ListItemStrategy implements MappingStrategy {

  @Override
  public MarkdownNode map(Node node, NodeMappingFactory nodeMappingFactory) {

    List<MarkdownNode> children = new ArrayList<>();
    for (var child = node.getFirstChild(); child != null; child = child.getNext()) {
      children.add(nodeMappingFactory.mapNode(child));
    }
    return new ListItem(children);
  }

  @Override
  public Node map(MarkdownNode node, NodeMappingFactory nodeMappingFactory) {
    var resultListItem = new org.commonmark.node.ListItem();
    var listItemParagraph = new org.commonmark.node.Paragraph();
    for (MarkdownNode child : ((ListItem) node).children()) {
      Node commonmarkChild = nodeMappingFactory.mapNode(child);
      listItemParagraph.appendChild(commonmarkChild);
    }
    resultListItem.appendChild(listItemParagraph);
    return resultListItem;
  }

  @Override
  public boolean canMap(Node node) {
    return node instanceof org.commonmark.node.ListItem;
  }

  @Override
  public boolean canMap(MarkdownNode markdownNode) {
    return markdownNode instanceof ListItem;
  }
}
