/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JacksonConfigIT {

  // Autowired ObjectMapper for IT
  @Autowired ObjectMapper autowiredObjectMapper;

  // Manually created ObjectMapper for Test
  static final ObjectMapper MANUAL_OBJECT_MAPPER = new JacksonConfig().objectMapper();

  static final LocalDate LOCAL_DATE_NOW = LocalDate.now();
  static final JavaDateWrapper JAVA_DATE_WRAPPER = new JavaDateWrapper(LOCAL_DATE_NOW);
  static final String JAVA_DATE_WRAPPER_JSON = "{\"localDate\":\"" + LOCAL_DATE_NOW + "\"}";

  @DisplayName("When autowiredObjectMapper is used, java date types must be serializable.")
  @Test
  void autowiredObjectMapperSerializeJavaDataTypes() throws JsonProcessingException {
    // Act
    String actual = autowiredObjectMapper.writeValueAsString(JAVA_DATE_WRAPPER);

    // Assert
    assertThat(actual).isEqualTo(JAVA_DATE_WRAPPER_JSON);
  }

  @DisplayName("When manualObjectMapper is used, java date types must be serializable.")
  @Test
  void manualObjectMapperSerializeJavaDataTypes() throws JsonProcessingException {
    // Act
    String actual = MANUAL_OBJECT_MAPPER.writeValueAsString(JAVA_DATE_WRAPPER);

    // Assert
    assertThat(actual).isEqualTo(JAVA_DATE_WRAPPER_JSON);
  }

  @DisplayName("When autowiredObjectMapper is used, java date types must be deserializable.")
  @Test
  void autowiredObjectMapperDeserializeJavaDataTypes() throws JsonProcessingException {
    // Act
    JavaDateWrapper actual =
        autowiredObjectMapper.readValue(JAVA_DATE_WRAPPER_JSON, JavaDateWrapper.class);

    // Assert
    assertThat(actual.localDate()).isEqualTo(JAVA_DATE_WRAPPER.localDate());
  }

  @DisplayName("When manualObjectMapper is used, java date types must be deserializable.")
  @Test
  void manualObjectMapperDeserializeJavaDataTypes() throws JsonProcessingException {
    // Act
    JavaDateWrapper actual =
        MANUAL_OBJECT_MAPPER.readValue(JAVA_DATE_WRAPPER_JSON, JavaDateWrapper.class);

    // Assert
    assertThat(actual.localDate()).isEqualTo(JAVA_DATE_WRAPPER.localDate());
  }

  private record JavaDateWrapper(LocalDate localDate) {}
}
