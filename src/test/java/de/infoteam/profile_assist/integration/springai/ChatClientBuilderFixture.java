// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
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
