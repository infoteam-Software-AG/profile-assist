// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import org.commonmark.node.Node;
import org.springframework.stereotype.Service;

@Service
public interface MappingStrategy {

  MarkdownNode map(Node node, NodeMappingFactory nodeMappingFactory);

  Node map(MarkdownNode node, NodeMappingFactory nodeMappingFactory);

  boolean canMap(Node node);

  boolean canMap(MarkdownNode markdownNode);
}
