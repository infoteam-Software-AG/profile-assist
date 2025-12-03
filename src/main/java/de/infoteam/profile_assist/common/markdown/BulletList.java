/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.common.markdown;

import java.util.List;
import lombok.Builder;
import lombok.Singular;

@Builder
public record BulletList(@Singular("item") List<ListItem> items) implements MarkdownNode {}
