package de.infoteam.profile_assist.integration.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.infoteam.profile_assist.domain.control.OptimizeProjectDesciptionService;
import de.infoteam.profile_assist.domain.entity.Persona;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

@SpringBootTest
@Slf4j
public class ChatServiceMT {

  private record Configuration(String model, String temperature) {}

  @Autowired private OptimizeProjectDesciptionService chatService;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private Environment environment;

  @Value("classpath:user-prompt.txt")
  private Resource userPromptResource;

  @Value("classpath:system-prompt.txt")
  private Resource systemPromptResource;

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
  @ValueSource(
      strings = {"anna_mueller", "beate_laurenz", "marianne_schmied", "lina_opitz", "lukas_weber"})
  public void askForPersonaOptimization_shouldUpdateUpdateTimestamp(String personaName) {

    String temperature = environment.getProperty("spring.ai.openai.chat.options.temperature", "-");
    String model = environment.getProperty("spring.ai.openai.chat.options.model", "-");
    Configuration configuration = new Configuration(model, temperature);
    try {
      String systemPrompt = systemPromptResource.getContentAsString(Charset.defaultCharset());
      String userPrompt = userPromptResource.getContentAsString(Charset.defaultCharset());
      Persona unoptimizedPersona = readPersonaJson(personaName);

      File testRunFolder =
          new File(
              "results"
                  + File.separator
                  + personaName
                  + File.separator
                  + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
                  + File.separator);
      testRunFolder.mkdirs();
      File configurationFile = new File(testRunFolder, "configuration.json");
      File systemPromptFile = new File(testRunFolder, "system-prompt.txt");
      File userPromptFile = new File(testRunFolder, "user-prompt.txt");

      Files.writeString(configurationFile.toPath(), objectMapper.writeValueAsString(configuration));
      Files.writeString(systemPromptFile.toPath(), systemPrompt);
      Files.writeString(userPromptFile.toPath(), userPrompt);

      log.info("Unoptimized persona: {}", objectMapper.writeValueAsString(unoptimizedPersona));

      // FIXME:
      Object optimizedPersona =
          chatService.optimizeProjectDescription(
              unoptimizedPersona.projectHistory().getFirst(), systemPrompt, userPrompt);

      //      assertThat(optimizedPersona.lastUpdate()).isAfter(unoptimizedPersona.lastUpdate());
      File personaFile = new File(testRunFolder, "optimized-persona.json");
      Files.writeString(
          personaFile.toPath(),
          objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(optimizedPersona));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
