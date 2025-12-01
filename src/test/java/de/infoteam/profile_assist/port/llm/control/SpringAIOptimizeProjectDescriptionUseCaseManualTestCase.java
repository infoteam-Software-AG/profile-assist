package de.infoteam.profile_assist.port.llm.control;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.domain.entity.Project;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
@Slf4j
@Disabled
class SpringAIOptimizeProjectDescriptionUseCaseManualTestCase {

  private record Configuration(String model, String temperature) {}

  @Autowired private SpringAiOptimizeProjectDescriptionUseCase chatUseCase;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private Environment environment;

  private Persona readPersonaJson(String personaName) throws IOException {
    try (InputStream in =
        Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(
                "personas" + File.separator + personaName + File.separator + "persona.json")) {
      return objectMapper.readValue(in, Persona.class);
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {"anna_mueller", "beate_laurenz"})
  void askForPersonaProjectDiscriptionOptimization_shouldUpdateUpdateTimestamp(String personaName) {

    String temperature = environment.getProperty("spring.ai.openai.chat.options.temperature", "-");
    String model = environment.getProperty("spring.ai.openai.chat.options.model", "-");
    Configuration configuration = new Configuration(model, temperature);
    try {
      Persona unoptimizedPersona = readPersonaJson(personaName);

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
      File configurationFile = new File(testRunFolder, "configuration.json");
      Files.writeString(configurationFile.toPath(), objectMapper.writeValueAsString(configuration));

      for (Project prj : unoptimizedPersona.projectHistory()) {
        var optimizationResult = chatUseCase.optimizeProjectDescription(prj);
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
}
