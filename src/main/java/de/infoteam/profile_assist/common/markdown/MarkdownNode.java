package de.infoteam.profile_assist.common.markdown;

public sealed interface MarkdownNode
    permits Document, Paragraph, Heading, Text, BulletList, ListItem {}
