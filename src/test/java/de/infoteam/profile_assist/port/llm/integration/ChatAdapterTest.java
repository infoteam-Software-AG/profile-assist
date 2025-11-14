package de.infoteam.profile_assist.port.llm.integration;

import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.domain.entity.Project;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ChatAdapterTest {
  @Autowired ChatAdapter chatAdapter;

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

    Persona unoptPersona = new Persona();
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
    log.info("unoptemized Persona: " + unoptPersona);
    log.info("optemized Persona: " + optPersona);
    Assertions.assertNotEquals(unoptPersona, optPersona);
  }
}
