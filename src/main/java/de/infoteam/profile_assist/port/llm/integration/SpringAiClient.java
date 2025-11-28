package de.infoteam.profile_assist.port.llm.integration;

import de.infoteam.profile_assist.port.llm.entity.OptimizationResultImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringAiClient {
  private final ChatClient chatClient;

  public <T> OptimizationResultImpl<T> sendPrompt(
      Class<T> resultEntity, String systemPrompt, String userPrompt) {
    return new OptimizationResultImpl<>(
        chatClient.prompt().system(systemPrompt).user(userPrompt).call().entity(resultEntity));
  }
}
