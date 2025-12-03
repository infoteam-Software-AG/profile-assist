/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.common.markdown;

import java.util.List;
import lombok.Builder;
import lombok.Singular;

@Builder
public record Document(@Singular("child") List<MarkdownNode> children) implements MarkdownNode {
  public static Document.DocumentBuilder builder() {
    return new DocumentBuilder();
  }
}
