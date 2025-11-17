package de.infoteam.profile_assist.common.markdown;

public interface MarkdownMapper {
  Document toAst(String markdown);

  String toMarkdown(Document ast);
}
