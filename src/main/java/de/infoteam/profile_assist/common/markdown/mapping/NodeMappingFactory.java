// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.common.markdown.mapping;

import de.infoteam.profile_assist.common.markdown.MarkdownNode;
import java.util.List;
import org.commonmark.node.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NodeMappingFactory {

  private final List<MappingStrategy> mappingStrategies;

  @Autowired
  public NodeMappingFactory(List<MappingStrategy> mappingStrategies) {
    this.mappingStrategies = mappingStrategies;
  }

  // map to commonmark
  public Node mapNode(MarkdownNode node) {
    return mappingStrategies.stream()
        .filter(mappingStrategy -> mappingStrategy.canMap(node))
        .map(mappingStrategy -> mappingStrategy.map(node, this))
        .findAny()
        .orElseThrow();
  }

  // Map to ast
  public MarkdownNode mapNode(Node node) {
    return mappingStrategies.stream()
        .filter(mappingStrategy -> mappingStrategy.canMap(node))
        .map(mappingStrategy -> mappingStrategy.map(node, this))
        .findAny()
        .orElseThrow();
  }
}
