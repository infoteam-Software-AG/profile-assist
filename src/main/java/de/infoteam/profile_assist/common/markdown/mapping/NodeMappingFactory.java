package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.BulletList;
import de.infoteam.profile_assist.common.markdown.Document;
import de.infoteam.profile_assist.common.markdown.Heading;
import de.infoteam.profile_assist.common.markdown.ListItem;
import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import de.infoteam.profile_assist.common.markdown.Paragraph;
import de.infoteam.profile_assist.common.markdown.StrongEmphasis;
import de.infoteam.profile_assist.common.markdown.Text;
import org.commonmark.node.Node;

public class NodeMappingFactory {

  private NodeMappingFactory() {}

  public static Node mapNode(MarkdownNode node) {
    return switch (node) {
      case Document doc -> new DocumentStrategy().mapToCommonMark(node);
      case Paragraph p -> new ParagraphStrategy().mapToCommonMark(node);
      case Heading h -> new HeadingStrategy().mapToCommonMark(node);
      case Text(String text) -> new TextStrategy().mapToCommonMark(node);
      case BulletList l -> new BulletListStrategy().mapToCommonMark(node);
      case ListItem item -> new ListItemStrategy().mapToCommonMark(node);
      case StrongEmphasis se -> new StrongEmphasisStrategy().mapToCommonMark(node);
      default -> throw new IllegalStateException("Unexpected markdown node type: " + node);
    };
  }

  public static MarkdownNode mapNode(Node node) {
    return switch (node) {
      case org.commonmark.node.Document doc -> new DocumentStrategy().mapToAST(node);
      case org.commonmark.node.Paragraph p -> new ParagraphStrategy().mapToAST(node);
      case org.commonmark.node.Heading h -> new HeadingStrategy().mapToAST(node);
      case org.commonmark.node.Text t -> new TextStrategy().mapToAST(node);
      case org.commonmark.node.BulletList list -> new BulletListStrategy().mapToAST(node);
      case org.commonmark.node.ListItem li -> new ListItemStrategy().mapToAST(node);
      case org.commonmark.node.StrongEmphasis se -> new StrongEmphasisStrategy().mapToAST(node);
      default -> throw new IllegalStateException("Unexpected CommonMark node type: " + node);
    };
  }
}
