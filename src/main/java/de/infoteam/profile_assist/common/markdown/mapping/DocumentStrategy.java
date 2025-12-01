package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.Document;
import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import java.util.ArrayList;
import java.util.List;
import org.commonmark.node.Node;
import org.springframework.stereotype.Component;

@Component
public class DocumentStrategy implements MappingStrategy {

  @Override
  public MarkdownNode map(Node commonMarkNode, NodeMappingFactory nodeMappingFactory) {
    List<MarkdownNode> documentChildNodes = new ArrayList<>();
    for (var child = commonMarkNode.getFirstChild(); child != null; child = child.getNext()) {
      documentChildNodes.add(nodeMappingFactory.mapNode(child));
    }
    return new Document(documentChildNodes);
  }

  @Override
  public Node map(MarkdownNode node, NodeMappingFactory nodeMappingFactory) {
    Document documentNode = (Document) node;
    var resultDocument = new org.commonmark.node.Document();
    for (MarkdownNode child : documentNode.children()) {
      resultDocument.appendChild(nodeMappingFactory.mapNode(child));
    }
    return resultDocument;
  }

  @Override
  public boolean canMap(Node node) {
    return node instanceof org.commonmark.node.Document;
  }

  @Override
  public boolean canMap(MarkdownNode markdownNode) {
    return markdownNode instanceof Document;
  }
}
