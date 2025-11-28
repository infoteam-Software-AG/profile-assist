package de.infoteam.profile_assist.common.markdown;

import lombok.Builder;

@Builder
public record StrongEmphasis(String delimiter, String text) implements MarkdownNode {}
