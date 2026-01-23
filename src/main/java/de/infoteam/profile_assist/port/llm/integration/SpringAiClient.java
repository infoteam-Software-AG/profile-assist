// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.integration;

import de.infoteam.profile_assist.port.llm.entity.OptimizationResultImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
@RequiredArgsConstructor
public class SpringAiClient {
  private final ChatClient chatClient;

  private final String sessionId = UUID.randomUUID().toString();

  public <T> OptimizationResultImpl<T> sendPrompt(
      Class<T> resultEntity, String systemPrompt, String userPrompt) {
    return new OptimizationResultImpl<>(
        chatClient
            .prompt()
            .user(u -> u.text(systemPrompt).metadata("sessionId", sessionId))
            .user(userPrompt)
            .call()
            .entity(resultEntity));
  }
}
