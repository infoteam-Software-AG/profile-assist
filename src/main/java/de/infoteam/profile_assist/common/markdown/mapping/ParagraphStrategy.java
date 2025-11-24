package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import de.infoteam.profile_assist.common.markdown.Paragraph;
import java.util.ArrayList;
import java.util.List;
import org.commonmark.node.Node;

public class ParagraphStrategy implements MappingStrategy {
  @Override
  public MarkdownNode mapToAST(Node node) {
    List<MarkdownNode> children = new ArrayList<>();
    for (var child = node.getFirstChild(); child != null; child = child.getNext()) {
      children.add(MappingStrategyFactory.executeStrategy(child));
    }
    return new Paragraph(children);
  }

  @Override
  public Node mapToCommonMark(MarkdownNode node) {
    var paragraph = new org.commonmark.node.Paragraph();
    for (MarkdownNode child : ((Paragraph) node).children()) {
      paragraph.appendChild(MappingStrategyFactory.executeStrategy(child));
    }
    return paragraph;
  }
}
