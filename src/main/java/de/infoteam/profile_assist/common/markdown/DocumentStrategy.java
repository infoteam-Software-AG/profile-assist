package de.infoteam.profile_assist.common.markdown;

import java.util.ArrayList;
import java.util.List;
import org.commonmark.node.Node;

public class DocumentStrategy implements MappingStrategy {

  @Override
  public MarkdownNode mapToAST(Node commonMarkNode) {
    List<MarkdownNode> documentChildren = new ArrayList<>();
    for (var commonMarkChild = commonMarkNode.getFirstChild();
        commonMarkChild != null;
        commonMarkChild = commonMarkChild.getNext()) {
      documentChildren.add(getCommonMarkChildStrategy(commonMarkChild).mapToAST(commonMarkChild));
    }
    return new Document(documentChildren);
  }

  @Override
  public Node mapToCommonMark(MarkdownNode node) {

    var documentNode = (Document) node;

    var resultDocument = new org.commonmark.node.Document();
    for (MarkdownNode astChild : documentNode.children()) {
      resultDocument.appendChild(getASTChildStrategy(astChild).mapToCommonMark(astChild));
    }
    return resultDocument;
  }
}
