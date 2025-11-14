package de.infoteam.profile_assist.port.llm.integration;

import static org.mockito.Answers.RETURNS_DEEP_STUBS;

import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.domain.entity.Project;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;

class ChatAdapterTest {

  private ChatAdapter chatAdapter;

  private ChatClient chatClient;

  private Persona unoptPersona = new Persona();

  private ChatClient.Builder chatClientBuilderMock;

  @BeforeEach
  void beforeEach() {
    chatClientBuilderMock = Mockito.mock(ChatClient.Builder.class);
    chatClient = Mockito.mock(ChatClient.class, RETURNS_DEEP_STUBS);

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
    unoptPersona.setStartingDate(new Date(2001, Calendar.SEPTEMBER, 11));
    unoptPersona.setLastUpdate(new Date(2025, Calendar.SEPTEMBER, 9));

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
    unoptPersona.setStartingDate(new Date(2001, Calendar.SEPTEMBER, 11));
    unoptPersona.setLastUpdate(new Date(2025, Calendar.SEPTEMBER, 9));
    // action
    Persona optPersona = chatAdapter.promptForPersonaOptimization(unoptPersona);
    // assertion
    Assertions.assertEquals(unoptPersona, optPersona);
  }
}
