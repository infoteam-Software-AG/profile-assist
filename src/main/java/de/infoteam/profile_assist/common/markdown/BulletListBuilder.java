package de.infoteam.profile_assist.common.markdown;

import java.util.function.Consumer;

public final class BulletListBuilder {

  private final DocumentBuilder parent;
  private final BulletList.BulletListBuilder builder = BulletList.builder();

  BulletListBuilder(DocumentBuilder parent) {
    this.parent = parent;
  }

  public BulletListBuilder addListItem(String text) {
    builder.item(
        ListItem.builder()
            .child(Paragraph.builder().child(Text.builder().text(text).build()).build())
            .build());
    return this;
  }

  public BulletListBuilder addListItem(Consumer<InlineBuilder> inlineConfig) {
    InlineBuilder inlineBuilder = new InlineBuilder();
    inlineConfig.accept(inlineBuilder);

    builder.item(
        ListItem.builder()
            .child(Paragraph.builder().children(inlineBuilder.buildChildren()).build())
            .build());
    return this;
  }

  public DocumentBuilder endBulletList() {
    parent.builder.child(builder.build());
    return parent;
  }
}
