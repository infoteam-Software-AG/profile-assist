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
    for (var child = commonMarkNode.getFirstChild(); child != null; child = child.getNext()) {
      documentChildNodes.add(NodeMappingFactory.mapNode(child));
    }
    return new Document(documentChildNodes);
  }

  @Override
  public Node mapToCommonMark(MarkdownNode node) {

    Document documentNode = (Document) node;

    var resultDocument = new org.commonmark.node.Document();
    for (MarkdownNode child : documentNode.children()) {
      resultDocument.appendChild(NodeMappingFactory.mapNode(child));
    }
    return resultDocument;
  }
}
