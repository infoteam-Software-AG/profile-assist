package de.infoteam.profile_assist.integration.chat;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.infoteam.profile_assist.domain.control.ChatService;
import de.infoteam.profile_assist.domain.entity.Persona;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ChatServiceMT {

  @Autowired private ChatService chatService;

  @Autowired private ObjectMapper objectMapper;

  private final Persona unoptimizedPersona = PersonaParser.readPersonaJson();

  @Test
  public void askForPersonaOptimization_shouldOptimizeTheProfile() throws JsonProcessingException {
    // Arrange
    log.info("Unoptimized persona: {}", this.objectMapper.writeValueAsString(unoptimizedPersona));

    // Act
    Persona optimizedPersona = chatService.askForPersonaOptimization(unoptimizedPersona);

    log.info("Optimized Persona: {}", this.objectMapper.writeValueAsString(optimizedPersona));
  }

  @Test
  public void askForPersonaOptimization_shouldUpdateUpdateTimestamp()
      throws JsonProcessingException {
    // Arrange
    log.info("Unoptimized persona: {}", this.objectMapper.writeValueAsString(unoptimizedPersona));

    // Act
    Persona optimizedPersona = chatService.askForPersonaOptimization(unoptimizedPersona);

    log.info("Optimized Persona: {}", this.objectMapper.writeValueAsString(optimizedPersona));
    assertThat(optimizedPersona.getLastUpdate()).isAfter(unoptimizedPersona.getLastUpdate());
  }
}
