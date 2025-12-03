/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.common.markdown;

public sealed interface MarkdownNode
    permits BulletList, Document, Heading, ListItem, Paragraph, StrongEmphasis, Text {}
