// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.control;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.infoteam.profile_assist.domain.entity.CallForBids;
import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.domain.entity.Project;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@Disabled
class SpringAIOptimizeProjectDescriptionUseCaseManualTestCase {

  @Autowired private SpringAiOptimizeProjectDescriptionUseCase chatUseCase;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private JsonReader jsonReader;

  @ParameterizedTest
  @ValueSource(strings = {"anna_mueller", "beate_laurenz"})
  void askForPersonaProjectDiscriptionOptimization_shouldUpdateUpdateTimestamp(String personaName) {

    try {
      Persona unoptimizedPersona = jsonReader.readPersonaJson(personaName);

      File testRunFolder =
          new File(
              "target"
                  + File.separator
                  + "manualTestResults"
                  + File.separator
                  + personaName
                  + File.separator
                  + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
                  + File.separator);
      testRunFolder.mkdirs();

      for (Project prj : unoptimizedPersona.projectHistory()) {
        var optimizationResult = chatUseCase.optimizeProjectDescription(prj, "");
        assertThat(optimizationResult.result().description()).isNotBlank();
        File personaFile =
            new File(
                testRunFolder,
                "optimized-project_" + unoptimizedPersona.projectHistory().indexOf(prj) + ".json");
        Files.writeString(
            personaFile.toPath(),
            objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(optimizationResult.result()));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {"anna_mueller"})
  void optimizePersonaProjects(String personaName) {
    try {
      Persona unoptimizedPersona = jsonReader.readPersonaJson(personaName);
      Persona.PersonaBuilder optimizedPersona = unoptimizedPersona.toBuilder();

      CallForBids callForBids = jsonReader.readBidJson("twe2");

      File testRunFolder =
          new File(
              "target"
                  + File.separator
                  + "manualTestResults"
                  + File.separator
                  + personaName
                  + File.separator
                  + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
                  + File.separator);
      testRunFolder.mkdirs();
      List<Project> optimizedProjects = new ArrayList<>();
      for (Project prj : unoptimizedPersona.projectHistory()) {
        assertThat(prj.description()).isNotBlank();
        var optimizationResult =
            chatUseCase.optimizeProjectDescription(prj, callForBids.description());
        optimizedProjects.add(optimizationResult.result());
      }
      optimizedPersona.projectHistory(optimizedProjects);
      optimizedPersona.build();
      File personaFile = new File(testRunFolder, "optimized-persona.json");
      Files.writeString(
          personaFile.toPath(),
          objectMapper
              .writerWithDefaultPrettyPrinter()
              .writeValueAsString(optimizedPersona.build()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
