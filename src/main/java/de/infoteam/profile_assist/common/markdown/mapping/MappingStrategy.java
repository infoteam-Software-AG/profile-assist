package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import org.commonmark.node.Node;

public interface MappingStrategy {

  MarkdownNode mapToAST(Node node);

  Node mapToCommonMark(MarkdownNode node);
}
