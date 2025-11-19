package de.infoteam.profile_assist.common.markdown;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.markdown.MarkdownRenderer;

import java.util.ArrayList;
import java.util.List;

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
    return toASTDocument(node);
  }

  @Override
  public String toMarkdown(Document ast) {
    if (ast == null) {
      return "";
    }
    var doc = toCommonmarkDocument(ast);
    return renderer.render(doc);
  }

  // -------- Markdown -> AST Document mapping -------- //
  private MarkdownNode mapNode(Node node) {
    return switch (node) {
      case org.commonmark.node.Document doc -> toASTDocument(doc);
      case org.commonmark.node.Heading h -> mapHeading(h);
      case org.commonmark.node.Paragraph p -> mapParagraph(p);
      case org.commonmark.node.BulletList list -> mapBulletList(list);
      case org.commonmark.node.ListItem li -> mapListItem(li);
      case org.commonmark.node.Text t -> new Text(t.getLiteral());
      case org.commonmark.node.StrongEmphasis se -> mapStrongEmphasis(se);
      default -> throw new IllegalStateException("Unexpected value: " + node);
    };
  }

  private StrongEmphasis mapStrongEmphasis(org.commonmark.node.StrongEmphasis se) {
    org.commonmark.node.Text newText = (org.commonmark.node.Text) se.getFirstChild();

    return new StrongEmphasis(se.getOpeningDelimiter(), newText.getLiteral());




//    String text = "";
//    StringBuilder stringBuilder = new StringBuilder();
//    stringBuilder
//        .append("** ")
//        .append(newText.getLiteral())
//        .append(" **");
//    text = stringBuilder.toString();
//    Text commonmarkText = new Text(text);
//    return commonmarkText;
  }

  private Document toASTDocument(Node doc) {
    List<MarkdownNode> children = new ArrayList<>();
    for (var child = doc.getFirstChild(); child != null; child = child.getNext()) {
      children.add(mapNode(child));
    }
    return new Document(children);
  }

  private Heading mapHeading(org.commonmark.node.Heading h) {
    List<MarkdownNode> children = new ArrayList<>();
    for (var child = h.getFirstChild(); child != null; child = child.getNext()) {
      children.add(mapNode(child));
    }
    return new Heading(h.getLevel(), children);
  }

  private Paragraph mapParagraph(org.commonmark.node.Paragraph p) {
    List<MarkdownNode> children = new ArrayList<>();
    for (var child = p.getFirstChild(); child != null; child = child.getNext()) {
      children.add(mapNode(child));
    }
    return new Paragraph(children);
  }

  private BulletList mapBulletList(org.commonmark.node.BulletList list) {
    List<ListItem> items = new ArrayList<>();
    for (var child = list.getFirstChild(); child != null; child = child.getNext()) {
      if (child instanceof org.commonmark.node.ListItem li) {
        items.add(mapListItem(li));
      }
    }
    return new BulletList(items);
  }

  private ListItem mapListItem(org.commonmark.node.ListItem li) {
    List<MarkdownNode> children = new ArrayList<>();
    for (var child = li.getFirstChild(); child != null; child = child.getNext()) {
      children.add(mapNode(child));
    }
    return new ListItem(children);
  }

  // ------- AST Document -> Commonmark mapping -------- //
  private Node toCommonmarkNode(MarkdownNode node) {
    return switch (node) {
      case Paragraph p -> toCommonmarkParagraph(p);
      case Heading h -> toCommonmarkHeading(h);
      case Text t -> new org.commonmark.node.Text(t.text());
      case BulletList l -> toCommonmarkBulletList(l);
      case ListItem item -> toCommonmarkListItem(item);
      case StrongEmphasis se -> toCommonmarkStrongEmphasis(se);
      default -> throw new IllegalStateException("Unexpected markdown type: " + node);
    };
  }

  private Node toCommonmarkStrongEmphasis(StrongEmphasis se) {
    var commonmarkSe = new org.commonmark.node.StrongEmphasis(se.delimiter());
    commonmarkSe.appendChild(new org.commonmark.node.Text(se.text()));
    return commonmarkSe;
  }

  private org.commonmark.node.Document toCommonmarkDocument(Document ast) {
    var document = new org.commonmark.node.Document();
    for (MarkdownNode child : ast.children()) {
      document.appendChild(toCommonmarkNode(child));
    }
    return document;
  }

  private org.commonmark.node.Paragraph toCommonmarkParagraph(Paragraph p) {
    var paragraph = new org.commonmark.node.Paragraph();
    for (MarkdownNode child : p.children()) {
      paragraph.appendChild(toCommonmarkNode(child));
    }
    return paragraph;
  }

  private org.commonmark.node.Heading toCommonmarkHeading(Heading h) {
    var heading = new org.commonmark.node.Heading();
    heading.setLevel(h.level());
    for (MarkdownNode child : h.children()) {
      heading.appendChild(toCommonmarkNode(child));
    }
    return heading;
  }

  private org.commonmark.node.BulletList toCommonmarkBulletList(BulletList list) {
    var bulletList = new org.commonmark.node.BulletList();
    for (ListItem item : list.items()) {
      bulletList.appendChild(toCommonmarkNode(item)); // ruft unten ListItem-Case auf
    }
    return bulletList;
  }

  private org.commonmark.node.ListItem toCommonmarkListItem(ListItem item) {
    var li = new org.commonmark.node.ListItem();
    for (MarkdownNode child : item.children()) {
      li.appendChild(toCommonmarkNode(child));
    }
    return li;
  }
}
