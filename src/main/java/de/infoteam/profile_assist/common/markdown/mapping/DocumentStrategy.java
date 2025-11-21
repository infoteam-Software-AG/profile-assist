package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.Document;
import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import java.util.ArrayList;
import java.util.List;
import org.commonmark.node.Node;

public class DocumentStrategy implements MappingStrategy {

  @Override
  public MarkdownNode mapToAST(Node commonMarkNode) {
    List<MarkdownNode> documentChildNodes = new ArrayList<>();
    for (var commonMarkChild = commonMarkNode.getFirstChild();
        commonMarkChild != null;
        commonMarkChild = commonMarkChild.getNext()) {
      documentChildNodes.add(getChildStrategy(commonMarkChild).mapToAST(commonMarkChild));
    }
    return new Document(documentChildNodes);
  }

  @Override
  public Node mapToCommonMark(MarkdownNode node) {

    Document documentNode = (Document) node;

    var resultDocument = new org.commonmark.node.Document();
    for (MarkdownNode astChild : documentNode.children()) {
      resultDocument.appendChild(getChildStrategy(astChild).mapToCommonMark(astChild));
    }
    return resultDocument;
  }
}
