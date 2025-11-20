package de.infoteam.profile_assist.common.markdown;

import java.util.List;

public record BulletList(List<ListItem> items) implements MarkdownNode {}
