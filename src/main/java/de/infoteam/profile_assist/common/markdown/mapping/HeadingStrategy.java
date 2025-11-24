package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.Heading;
import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import java.util.ArrayList;
import java.util.List;
import org.commonmark.node.Node;

public class HeadingStrategy implements MappingStrategy {
  @Override
  public MarkdownNode mapToAST(Node node) {
    int headingLevel = ((org.commonmark.node.Heading) node).getLevel();
    List<MarkdownNode> children = new ArrayList<>();
    for (var child = node.getFirstChild(); child != null; child = child.getNext()) {
      children.add(MappingStrategyFactory.executeStrategy(child));
    }

    return new Heading(headingLevel, children);
  }

  @Override
  public Node mapToCommonMark(MarkdownNode node) {
    var resultHeading = new org.commonmark.node.Heading();

    resultHeading.setLevel(((Heading) node).level());

    for (MarkdownNode child : (((Heading) node).children())) {
      resultHeading.appendChild(MappingStrategyFactory.executeStrategy(child));
    }
    return resultHeading;
  }
}
