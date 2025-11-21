package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import de.infoteam.profile_assist.common.markdown.StrongEmphasis;
import org.commonmark.node.Node;

public class StrongEmphasisStrategy implements MappingStrategy {
  @Override
  public MarkdownNode mapToAST(Node node) {
    org.commonmark.node.Text newText = (org.commonmark.node.Text) node.getFirstChild();
    return new StrongEmphasis(
        ((org.commonmark.node.StrongEmphasis) node).getOpeningDelimiter(), newText.getLiteral());
  }

  @Override
  public Node mapToCommonMark(MarkdownNode node) {
    var resultStrongEmphasis =
        new org.commonmark.node.StrongEmphasis(((StrongEmphasis) node).delimiter());
    resultStrongEmphasis.appendChild(new org.commonmark.node.Text(((StrongEmphasis) node).text()));
    return resultStrongEmphasis;
  }
}
