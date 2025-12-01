package de.infoteam.profile_assist.port.llm.integration;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.domain.entity.Project;
import de.infoteam.profile_assist.port.llm.entity.OptimizationResultImpl;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;

class SpringAiClientTest {

  @Test
  void sendPrompt() {
    ChatClient chatClient = mock(ChatClient.class, RETURNS_DEEP_STUBS);

    SpringAiClient springAiClient = new SpringAiClient(chatClient);
    List<String> techStack = List.of("Java", "Spring", "SQL", "HTML", "CSS");

    Project project = new Project("Project ABC", "B2C Webshop", Collections.emptyList());

    var unoptimizedPersona =
        new Persona(
            UUID.randomUUID(),
            "Software Guy",
            techStack,
            List.of("ISTQB", "SQL ISLAND"),
            List.of(project),
            LocalDate.of(2024, Month.JANUARY, 1),
            LocalDate.of(2025, Month.JANUARY, 1));

    when(chatClient.prompt().system(anyString()).user(anyString()).call().entity(Persona.class))
        .thenReturn(unoptimizedPersona);

    OptimizationResultImpl<Persona> optimizedPersona =
        springAiClient.sendPrompt(Persona.class, "systemprompt", "userprompt");

    then(optimizedPersona.result()).isNotNull();
  }
}
