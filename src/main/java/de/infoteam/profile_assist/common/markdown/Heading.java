package de.infoteam.profile_assist.common.markdown;

import java.util.List;

public record Heading(int level, List<MarkdownNode> children) implements MarkdownNode {}
