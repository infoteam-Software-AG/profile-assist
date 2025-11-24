package de.infoteam.profile_assist.common.markdown;

import de.infoteam.profile_assist.common.markdown.mapping.NodeMappingFactory;
import java.util.List;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.markdown.MarkdownRenderer;

public class CommonmarkMarkdownMapper implements MarkdownMapper {
  private final Parser parser;
  private final MarkdownRenderer renderer;

  public CommonmarkMarkdownMapper(Parser parser, MarkdownRenderer renderer) {
    this.parser = parser;
    this.renderer = renderer;
  }

  @Override
  public Document toAst(String markdown) {
    if (markdown == null) {
      return new Document(List.of());
    }
    Node node = parser.parse(markdown);

    return (Document) NodeMappingFactory.mapNode(node);
  }

  @Override
  public String toMarkdown(Document ast) {
    if (ast == null) {
      return "";
    }
    var doc = NodeMappingFactory.mapNode(ast);
    return renderer.render(doc);
  }
}
