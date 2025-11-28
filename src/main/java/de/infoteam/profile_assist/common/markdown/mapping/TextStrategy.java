package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import de.infoteam.profile_assist.common.markdown.Text;
import org.commonmark.node.Node;

public class TextStrategy implements MappingStrategy {
  @Override
  public MarkdownNode mapToAST(Node node) {
    return new Text(((org.commonmark.node.Text) node).getLiteral());
  }

  @Override
  public Node mapToCommonMark(MarkdownNode node) {
    String resultText = ((Text) node).text();
    return new org.commonmark.node.Text(resultText);
  }
}
