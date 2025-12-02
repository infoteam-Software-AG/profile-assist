package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import de.infoteam.profile_assist.common.markdown.StrongEmphasis;
import org.commonmark.node.Node;
import org.springframework.stereotype.Component;

@Component
public class StrongEmphasisStrategy implements MappingStrategy {

  @Override
  public MarkdownNode map(Node node, NodeMappingFactory nodeMappingFactory) {
    org.commonmark.node.Text newText = (org.commonmark.node.Text) node.getFirstChild();
    return new StrongEmphasis(
        ((org.commonmark.node.StrongEmphasis) node).getOpeningDelimiter(),
        newText.getLiteral());
  }

  @Override
  public Node map(MarkdownNode node, NodeMappingFactory nodeMappingFactory) {
    var resultStrongEmphasis =
        new org.commonmark.node.StrongEmphasis(((StrongEmphasis) node).delimiter());
    resultStrongEmphasis.appendChild(new org.commonmark.node.Text(((StrongEmphasis) node).text()));
    return resultStrongEmphasis;
  }

  @Override
  public boolean canMap(Node node) {
    return node instanceof org.commonmark.node.StrongEmphasis;
  }

  @Override
  public boolean canMap(MarkdownNode markdownNode) {
    return markdownNode instanceof StrongEmphasis;
  }
}
