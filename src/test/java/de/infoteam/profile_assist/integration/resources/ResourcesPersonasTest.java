/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.integration.resources;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.integration.JacksonConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ResourcesPersonasTest {

  private static final Path PERSONAS_BASE_PATH = Paths.get("src", "test", "resources", "personas");
  private static final String PERSONA_JSON_FILE = "persona.json";
  private static final ObjectMapper OBJECT_MAPPER = new JacksonConfig().objectMapper();

  @DisplayName("When persona files are scanned and read, then all personas can be deserialized")
  @ParameterizedTest(name = "Case {index}: file={0}")
  @MethodSource("personaJsonFiles")
  void allPersonaJsonFilesMustBeValid(Path personaFile) {
    // Act & Assert
    assertThatCode(() -> OBJECT_MAPPER.readValue(personaFile.toFile(), Persona.class))
        .doesNotThrowAnyException();
  }

  static Stream<Path> personaJsonFiles() throws IOException {
    assertThat(PERSONAS_BASE_PATH).exists();

    try (Stream<Path> paths = Files.walk(PERSONAS_BASE_PATH)) {
      List<Path> files =
          paths.filter(p -> p.getFileName().toString().equals(PERSONA_JSON_FILE)).toList();

      assertThat(files).isNotEmpty();

      return files.stream();
    }
  }
}
