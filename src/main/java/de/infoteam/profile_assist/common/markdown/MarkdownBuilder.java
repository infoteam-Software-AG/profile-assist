/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.common.markdown;

public final class MarkdownBuilder {

  private MarkdownBuilder() {}

  public static DocumentBuilder document() {
    return new DocumentBuilder();
  }
}
