package de.infoteam.profile_assist.port.llm.integration;

import static org.mockito.Answers.RETURNS_DEEP_STUBS;

import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.domain.entity.Project;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.ai.chat.client.advisor.api.Advisor;

class ChatAdapterTest {

  private ChatAdapter chatAdapter;

  private Persona unoptimizedPersona;

  @BeforeEach
  void beforeEach() {
    Builder chatClientBuilderMock = Mockito.mock(Builder.class);
    ChatClient chatClient = Mockito.mock(ChatClient.class, RETURNS_DEEP_STUBS);

    Mockito.when(chatClientBuilderMock.build()).thenReturn(chatClient);

    List<String> techStack = List.of("Java", "Spring", "SQL", "HTML", "CSS");

    Project project = new Project("Project ABC", "B2C Webshop", techStack);

    unoptimizedPersona =
        new Persona(
            UUID.randomUUID(),
            "Software Guy",
            techStack,
            List.of("ISTQB", "SQL ISLAND"),
            List.of(project),
            LocalDate.of(2024, Month.JANUARY, 1),
            LocalDate.of(2025, Month.JANUARY, 1));

    Mockito.when(
            chatClient
                .prompt()
                .advisors(Mockito.any(Advisor.class))
                .system(Mockito.anyString())
                .user(Mockito.anyString())
                .tools(Mockito.any())
                .call()
                .entity(Persona.class))
        .thenReturn(unoptimizedPersona);
    chatAdapter = new ChatAdapter(chatClientBuilderMock);
  }

  @Test
  void optimizePersona() {
    // action
    Persona optimizedPersona =
        chatAdapter.optimizePersona(unoptimizedPersona, "systempromot", "userprompt");
    // assertion
    Assertions.assertThat(optimizedPersona).isEqualTo(unoptimizedPersona);
  }
}
