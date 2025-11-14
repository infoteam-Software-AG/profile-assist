package de.infoteam.profile_assist.port.llm.integration;

import static org.mockito.Answers.RETURNS_DEEP_STUBS;

import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.domain.entity.Project;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;

class ChatAdapterTest {

  private ChatAdapter chatAdapter;

  private Persona unoptPersona = new Persona();

  @BeforeEach
  void beforeEach() {
    ChatClient.Builder chatClientBuilderMock = Mockito.mock(ChatClient.Builder.class);
    ChatClient chatClient = Mockito.mock(ChatClient.class, RETURNS_DEEP_STUBS);

    Mockito.when(chatClientBuilderMock.build()).thenReturn(chatClient);

    Project projA = new Project();

    projA.setName("Project ABC");

    projA.setDescription("B2C Webshop");

    List<String> techStack = List.of("Java", "Spring", "SQL", "HTML", "CSS");

    projA.setTechnologies(techStack);

    List<String> certificates = List.of("ISTQB", "SQL ISLAND");

    List<Project> projects = new ArrayList<>();
    projects.add(projA);

    unoptPersona = new Persona();
    unoptPersona.setId(UUID.randomUUID());
    unoptPersona.setJobTitle("Softwareheini");
    unoptPersona.setSkills(techStack);
    unoptPersona.setCertificates(certificates);
    unoptPersona.setProjectHistory(projects);
    unoptPersona.setStartingDate(Date.from(Instant.parse("2001-09-11T00:00:00Z")));
    unoptPersona.setLastUpdate(Date.from(Instant.parse("2001-09-30T00:00:00Z")));

    Mockito.when(
            chatClient
                .prompt()
                .system(Mockito.anyString())
                .user(Mockito.anyString())
                .tools(Mockito.any())
                .call()
                .entity(Persona.class))
        .thenReturn(unoptPersona);
    chatAdapter = new ChatAdapter(chatClientBuilderMock);
  }

  @Test
  void promptForPersonaOptimization() {
    // pre-action
    Project projA = new Project();

    projA.setName("Project ABC");

    projA.setDescription("B2C Webshop");

    List<String> techStack = new ArrayList<>();
    techStack.add("Java");
    techStack.add("Spring");
    techStack.add("SQL");
    techStack.add("HTML");
    techStack.add("CSS");
    projA.setTechnologies(techStack);

    List<String> certificates = new ArrayList<>();
    certificates.add("ISTQB");
    certificates.add("SQL Island");

    List<Project> projects = new ArrayList<>();
    projects.add(projA);

    unoptPersona.setId(UUID.randomUUID());
    unoptPersona.setJobTitle("Softwareheini");
    unoptPersona.setSkills(techStack);
    unoptPersona.setCertificates(certificates);
    unoptPersona.setProjectHistory(projects);
    unoptPersona.setStartingDate(Date.from(Instant.parse("2001-09-11T00:00:00Z")));
    unoptPersona.setLastUpdate(Date.from(Instant.parse("2001-09-30T00:00:00Z")));
    // action
    Persona optPersona = chatAdapter.promptForPersonaOptimization(unoptPersona);
    // assertion
    Assertions.assertThat(unoptPersona).isEqualTo(optPersona);
  }
}
