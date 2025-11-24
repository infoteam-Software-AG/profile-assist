package de.infoteam.profile_assist.common.markdown;

import java.util.ArrayList;
import java.util.List;

final class InlineBuilder {

  private final List<MarkdownNode> children = new ArrayList<>();

  public InlineBuilder text(String text) {
    children.add(Text.builder().text(text).build());
    return this;
  }

  public InlineBuilder strong(String text) {
    children.add(
      StrongEmphasis.builder()
        .delimiter("**")
        .text(text)
        .build()
    );
    return this;
  }

  List<MarkdownNode> buildChildren() {
    return List.copyOf(children);
  }
}
