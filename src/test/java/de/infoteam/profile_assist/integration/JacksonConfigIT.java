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
public class JacksonConfigIT {

  // Autowired ObjectMapper for IT
  @Autowired ObjectMapper autowiredObjectMapper;

  // Manually created ObjectMapper for Test
  ObjectMapper manualObjectMapper = new JacksonConfig().objectMapper();

  LocalDate LOCAL_DATE_NOW = LocalDate.now();
  JavaDateWrapper JAVA_DATE_WRAPPER = new JavaDateWrapper(LOCAL_DATE_NOW);
  String JAVA_DATE_WRAPPER_JSON = "{\"localDate\":\"" + LOCAL_DATE_NOW.toString() + "\"}";

  @DisplayName("When autowiredObjectMapper is used, java time types must be serializable.")
  @Test
  void autowiredObjectMapperSerializeJavaDataTypes() throws JsonProcessingException {
    // Act
    String actual = autowiredObjectMapper.writeValueAsString(JAVA_DATE_WRAPPER);

    // Assert
    assertThat(actual).isEqualTo(JAVA_DATE_WRAPPER_JSON);
  }

  @DisplayName("When manualObjectMapper is used, java time types must be serializable.")
  @Test
  void manualObjectMapperSerializeJavaDataTypes() throws JsonProcessingException {
    // Act
    String actual = manualObjectMapper.writeValueAsString(JAVA_DATE_WRAPPER);

    // Assert
    assertThat(actual).isEqualTo(JAVA_DATE_WRAPPER_JSON);
  }

  @DisplayName("When autowiredObjectMapper is used, java time types must be deserializable.")
  @Test
  void autowiredObjectMapperDeserializeJavaDataTypes() throws JsonProcessingException {
    // Act
    JavaDateWrapper actual =
        autowiredObjectMapper.readValue(JAVA_DATE_WRAPPER_JSON, JavaDateWrapper.class);

    // Assert
    assertThat(actual.localDate()).isEqualTo(JAVA_DATE_WRAPPER.localDate());
  }

  @DisplayName("When manualObjectMapper is used, java time types must be deserializable.")
  @Test
  void manualObjectMapperDeserializeJavaDataTypes() throws JsonProcessingException {
    // Act
    JavaDateWrapper actual =
        manualObjectMapper.readValue(JAVA_DATE_WRAPPER_JSON, JavaDateWrapper.class);

    // Assert
    assertThat(actual.localDate()).isEqualTo(JAVA_DATE_WRAPPER.localDate());
  }

  private record JavaDateWrapper(LocalDate localDate) {}
}
