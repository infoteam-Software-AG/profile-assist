/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.common.markdown;

import java.util.function.Consumer;

public final class DocumentBuilder {
  final Document.DocumentBuilder builder = Document.builder();

  public DocumentBuilder addParagraph(String text) {
    builder.child(Paragraph.builder().child(Text.builder().text(text).build()).build());
    return this;
  }

  public DocumentBuilder addParagraph(Consumer<InlineBuilder> inlineConfig) {
    InlineBuilder inlineBuilder = new InlineBuilder();
    inlineConfig.accept(inlineBuilder);

    builder.child(Paragraph.builder().children(inlineBuilder.buildChildren()).build());
    return this;
  }

  public DocumentBuilder addHeading(int level, String text) {
    builder.child(Heading.builder().level(level).child(Text.builder().text(text).build()).build());
    return this;
  }

  public BulletListBuilder addBulletList() {
    return new BulletListBuilder(this);
  }

  public Document build() {
    return builder.build();
  }
}
