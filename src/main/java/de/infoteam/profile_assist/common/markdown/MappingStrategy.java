package de.infoteam.profile_assist.common.markdown;

import org.commonmark.node.Node;

public interface MappingStrategy {

  MarkdownNode mapToAST(Node node);

  Node mapToCommonMark(MarkdownNode node);

  default MappingStrategy getASTChildStrategy(MarkdownNode node) {
    return switch (node) {
      case Paragraph p -> new ParagraphStrategy();
      case Heading h -> new HeadingStrategy();
      case Text(String text) -> new TextStrategy();
      case BulletList l -> new BulletListStrategy();
      case ListItem item -> new ListItemStrategy();
      case StrongEmphasis se -> new StrongEmphasisStrategy();
      default -> throw new IllegalStateException("Unexpected markdown node type: " + node);
    };
  }

  default MappingStrategy getCommonMarkChildStrategy(Node node) {
    return switch (node) {
      case org.commonmark.node.Document doc -> new DocumentStrategy();
      case org.commonmark.node.Paragraph p -> new ParagraphStrategy();
      case org.commonmark.node.Heading h -> new HeadingStrategy();
      case org.commonmark.node.Text t -> new TextStrategy();
      case org.commonmark.node.BulletList list -> new BulletListStrategy();
      case org.commonmark.node.ListItem li -> new ListItemStrategy();
      case org.commonmark.node.StrongEmphasis se -> new StrongEmphasisStrategy();
      default -> throw new IllegalStateException("Unexpected CommonMark node type: " + node);
    };
  }
}
