/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.integration.springai;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.NoopApiKey;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ChatClientBuilderFixture {

  public static ChatClient.Builder noop() {
    return ChatClient.builder(
        OpenAiChatModel.builder()
            .openAiApi(OpenAiApi.builder().apiKey(new NoopApiKey()).build())
            .build());
  }
}
