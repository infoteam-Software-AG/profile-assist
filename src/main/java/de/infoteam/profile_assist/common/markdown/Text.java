package de.infoteam.profile_assist.common.markdown;

import lombok.Builder;

@Builder
public record Text(String text) implements MarkdownNode {}
