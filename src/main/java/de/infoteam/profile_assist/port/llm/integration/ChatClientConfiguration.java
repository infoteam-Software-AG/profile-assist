package de.infoteam.profile_assist.port.llm.integration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {
  @Bean
  public ChatClient chatClient(ChatClient.Builder builder) {
    return builder.defaultAdvisors(new SimpleLoggerAdvisor()).build();
  }
}
