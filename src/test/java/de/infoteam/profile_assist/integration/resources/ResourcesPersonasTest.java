package de.infoteam.profile_assist.integration.resources;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.infoteam.profile_assist.domain.entity.Persona;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ResourcesPersonasTest {

  private static final Path PERSONAS_BASE = Paths.get("src", "test", "resources", "personas");

  private static final String PERSONA_JSON_FILE = "persona.json";

  @DisplayName(
      "When persona files are scanned and read, then all persona.json files can be deserialized")
  @ParameterizedTest(name = "Case {index}: file={0}")
  @MethodSource("personaJsonFiles")
  void allPersonaJsonFilesMustBeValid(Path personaFile) {
    // Arrange
    ObjectMapper mapper = new ObjectMapper();

    // Act & Assert
    assertThatCode(() -> mapper.readValue(personaFile.toFile(), Persona.class))
        .doesNotThrowAnyException();
  }

  static Stream<Path> personaJsonFiles() throws IOException {
    assertThat(PERSONAS_BASE).exists();

    try (Stream<Path> paths = Files.walk(PERSONAS_BASE)) {
      List<Path> files =
          paths.filter(p -> p.getFileName().toString().equals(PERSONA_JSON_FILE)).toList();

      assertThat(files).isNotEmpty();

      return files.stream();
    }
  }
}
