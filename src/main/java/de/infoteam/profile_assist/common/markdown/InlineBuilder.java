// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
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
    children.add(StrongEmphasis.builder().delimiter("**").text(text).build());
    return this;
  }

  List<MarkdownNode> buildChildren() {
    return List.copyOf(children);
  }
}
