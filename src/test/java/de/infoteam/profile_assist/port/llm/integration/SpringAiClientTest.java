/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.port.llm.integration;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.domain.entity.Project;
import de.infoteam.profile_assist.domain.entity.Skills;
import de.infoteam.profile_assist.port.llm.entity.OptimizationResultImpl;
import java.time.LocalDate;
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

    Project project =
        new Project(
            "Project ABC",
            "B2C Webshop",
            List.of("Java", "Spring Boot"),
            "TIME_PERIOD",
            "BUSINESS_SECTOR",
            12,
            "ROLE",
            "SPECIALIZED_FOCUS",
            List.of("MyPersonalContribution", "AnotherPersonalContribution"),
            List.of("ISO", "IEC"));

    var unoptimizedPersona =
        new Persona(
            UUID.randomUUID(),
            "JOB_TITLE",
            "NAME",
            Collections.emptyList(),
            5,
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Skills.builder()
                .businessSectors(Collections.emptyList())
                .languages(Collections.emptyList())
                .programmingLanguages(Collections.emptyList())
                .methodicalExpertise(Collections.emptyList())
                .tools(Collections.emptyList())
                .operatingSystems(Collections.emptyList())
                .databases(Collections.emptyList())
                .build(),
            List.of(project),
            LocalDate.now(),
            LocalDate.now());

    when(chatClient.prompt().system(anyString()).user(anyString()).call().entity(Persona.class))
        .thenReturn(unoptimizedPersona);

    OptimizationResultImpl<Persona> optimizedPersona =
        springAiClient.sendPrompt(Persona.class, "systemprompt", "userprompt");

    then(optimizedPersona.result()).isNotNull();
  }
}
