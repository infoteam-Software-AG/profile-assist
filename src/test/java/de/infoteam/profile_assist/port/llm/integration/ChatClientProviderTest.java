// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.infoteam.profile_assist.integration.springai.ChatClientBuilderFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;

@ExtendWith(MockitoExtension.class)
class ChatClientProviderTest {

  ChatClientProvider chatClientProvider = new ChatClientProvider();

  @Captor ArgumentCaptor<Advisor> advisorArgumentCaptor;

  @DisplayName("Ensure ChatClient is configured as expected")
  @Test
  void chatClient_returnsExpectedClient() {
    var chatClientBuilder = spy(ChatClientBuilderFixture.noop());

    var chatClient = chatClientProvider.chatClient(chatClientBuilder);

    assertThat(chatClient).isNotNull();
    verify(chatClientBuilder, times(1)).defaultAdvisors(advisorArgumentCaptor.capture());
    verify(chatClientBuilder, times(1)).build();
    assertThat(advisorArgumentCaptor.getValue()).isInstanceOf(SimpleLoggerAdvisor.class);
  }
}
