package de.infoteam.profile_assist.common.markdown;

import java.util.function.Consumer;

public final class MarkdownBuilder {
  private MarkdownBuilder() {}

  public static DocumentBuilder document() {
    return new DocumentBuilder();
  }

  public static final class DocumentBuilder {
    private final Document.DocumentBuilder builder = Document.builder();

    public DocumentBuilder addParagraph(String text) {
      builder.child(
        Paragraph.builder()
          .child(Text.builder().text(text).build())
          .build()
      );
      return this;
    }

    public DocumentBuilder addParagraph(Consumer<InlineBuilder> inlineConfig) {
      InlineBuilder inlineBuilder = new InlineBuilder();
      inlineConfig.accept(inlineBuilder);

      builder.child(
        Paragraph.builder()
          .children(inlineBuilder.buildChildren())
          .build()
      );
      return this;
    }

    public DocumentBuilder addHeading(int level, String text) {
      builder.child(
        Heading.builder()
          .level(level)
          .child(Text.builder().text(text).build())
          .build()
      );
      return this;
    }

    public BulletListBuilder addBulletList() {
      return new BulletListBuilder(this);
    }

    public Document build() {
      return builder.build();
    }
  }

  public static final class BulletListBuilder {
    private final DocumentBuilder parent;
    private final BulletList.BulletListBuilder builder = BulletList.builder();

    BulletListBuilder(DocumentBuilder parent) {
      this.parent = parent;
    }

    public BulletListBuilder addListItem(String text) {
      builder.item(
        ListItem.builder()
          .child(
            Paragraph.builder()
              .child(Text.builder().text(text).build())
              .build()
          ).build()
      );
      return this;
    }

    public BulletListBuilder addListItem(Consumer<InlineBuilder> inlineConfig) {
      InlineBuilder inlineDsl = new InlineBuilder();
      inlineConfig.accept(inlineDsl);

      builder.item(
        ListItem.builder()
          .child(
            Paragraph.builder()
              .children(inlineDsl.buildChildren())
              .build()
          )
          .build()
      );
      return this;
    }

    public DocumentBuilder endBulletList() {
      parent.builder.child(builder.build());
      return parent;
    }
  }

  public static final class InlineBuilder {
    private final java.util.List<MarkdownNode> children = new java.util.ArrayList<>();

    public InlineBuilder text(String text) {
      children.add(Text.builder().text(text).build());
      return this;
    }

    public InlineBuilder strong(String text) {
      children.add(StrongEmphasis.builder()
        .delimiter("**")
        .text(text)
        .build());
      return this;
    }

    java.util.List<MarkdownNode> buildChildren() {
      return java.util.List.copyOf(children);
    }
  }
}
