package de.infoteam.profile_assist.common.markdown;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FlexmarkMarkdownMapper implements MarkdownMapper {
  private final Parser parser;
  private final Formatter formatter;

  public FlexmarkMarkdownMapper(Parser parser, Formatter formatter) {
    this.parser = parser;
    this.formatter = formatter;
  }

  @Override
  public Document toAst(String markdown) {
    com.vladsch.flexmark.util.ast.Document doc = parser.parse(markdown == null ? "" : markdown);
    return mapDocument(doc);
  }

  @Override
  public String toMarkdown(Document ast) {
    if (ast == null) return "";
    var doc = toFlexmarkDocument(ast);
    return formatter.render(doc).trim();
  }

  // Markdown mapping
  private MarkdownNode mapNode(Node node) {
    return switch (node) {
      case com.vladsch.flexmark.util.ast.Document doc -> mapDocument(doc);
      case com.vladsch.flexmark.ast.Heading h -> mapHeading(h);
      case com.vladsch.flexmark.ast.Paragraph p -> mapParagraph(p);
      case com.vladsch.flexmark.ast.BulletList list -> mapBulletList(list);
      case com.vladsch.flexmark.ast.ListItem li -> mapListItem(li);
      case com.vladsch.flexmark.ast.Text t -> new Text(t.getChars().toString());
      default -> new Text(node.getChars().toString());
    };
  }

  private com.vladsch.flexmark.util.ast.Document toFlexmarkDocument(Document ast) {
    var document =
        new com.vladsch.flexmark.util.ast.Document(parser.getOptions(), BasedSequence.NULL);
    for (MarkdownNode child : ast.children()) {
      document.appendChild(toFlexmarkNode(child));
    }
    return document;
  }

  private Document mapDocument(com.vladsch.flexmark.util.ast.Document doc) {
    List<MarkdownNode> children = new ArrayList<>();
    for (var child = doc.getFirstChild(); child != null; child = child.getNext()) {
      children.add(mapNode(child));
    }
    return new Document(children);
  }

  private Heading mapHeading(com.vladsch.flexmark.ast.Heading h) {
    List<MarkdownNode> children = new ArrayList<>();
    for (var child = h.getFirstChild(); child != null; child = child.getNext()) {
      children.add(mapNode(child));
    }
    return new Heading(h.getLevel(), children);
  }

  private Paragraph mapParagraph(com.vladsch.flexmark.ast.Paragraph p) {
    List<MarkdownNode> children = new ArrayList<>();
    for (var child = p.getFirstChild(); child != null; child = child.getNext()) {
      children.add(mapNode(child));
    }
    return new Paragraph(children);
  }

  private BulletList mapBulletList(com.vladsch.flexmark.ast.BulletList list) {
    List<ListItem> items = new ArrayList<>();
    for (var child = list.getFirstChild(); child != null; child = child.getNext()) {
      if (child instanceof com.vladsch.flexmark.ast.ListItem li) {
        items.add(mapListItem(li));
      }
    }
    return new BulletList(items);
  }

  private ListItem mapListItem(com.vladsch.flexmark.ast.ListItem li) {
    List<MarkdownNode> children = new ArrayList<>();
    for (var child = li.getFirstChild(); child != null; child = child.getNext()) {
      children.add(mapNode(child));
    }
    return new ListItem(children);
  }

  // AST mapping
  private Node toFlexmarkNode(MarkdownNode node) {
    return switch (node) {
      case Paragraph p -> toFlexmarkParagraph(p);
      case Heading h -> toFlexmarkHeading(h);
      case Text t -> new com.vladsch.flexmark.ast.Text(t.text());
      case BulletList l -> toFlexmarkBulletList(l);
      case ListItem item -> toFlexmarkListItem(item);
      default -> throw new IllegalStateException("Unexpected markdown type: " + node);
    };
  }

  private com.vladsch.flexmark.ast.Paragraph toFlexmarkParagraph(Paragraph p) {
    var paragraph = new com.vladsch.flexmark.ast.Paragraph();
    for (MarkdownNode child : p.children()) {
      paragraph.appendChild(toFlexmarkNode(child));
    }
    return paragraph;
  }

  private com.vladsch.flexmark.ast.Heading toFlexmarkHeading(Heading h) {
    var heading = new com.vladsch.flexmark.ast.Heading();
    heading.setLevel(h.level());
    for (MarkdownNode child : h.children()) {
      heading.appendChild(toFlexmarkNode(child));
    }
    return heading;
  }

  private com.vladsch.flexmark.ast.BulletList toFlexmarkBulletList(BulletList list) {
    var bulletList = new com.vladsch.flexmark.ast.BulletList();
    for (ListItem item : list.items()) {
      bulletList.appendChild(toFlexmarkNode(item)); // ruft unten ListItem-Case auf
    }
    return bulletList;
  }

  private com.vladsch.flexmark.ast.BulletListItem toFlexmarkListItem(ListItem item) {
    var li = new com.vladsch.flexmark.ast.BulletListItem();
    for (MarkdownNode child : item.children()) {
      li.appendChild(toFlexmarkNode(child));
    }
    return li;
  }
}
