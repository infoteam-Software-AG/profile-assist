package de.infoteam.profile_assist.common.markdown;

import java.util.List;

public record ListItem(List<MarkdownNode> children) implements MarkdownNode {}
