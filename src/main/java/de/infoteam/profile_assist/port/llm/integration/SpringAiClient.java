// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.integration;

import de.infoteam.profile_assist.port.llm.entity.OptimizationResultImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringAiClient {

  private final ChatClient chatClient;
  private final ChatMemory chatMemory;

  public <T> OptimizationResultImpl<T> sendPrompt(
      Class<T> resultEntity, String systemPrompt, String userPrompt, String conversationID) {
    return new OptimizationResultImpl<>(
        chatClient.prompt().advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationID)).system(systemPrompt).user(userPrompt).call().entity(resultEntity));
  }
}
