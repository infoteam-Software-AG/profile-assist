package de.infoteam.profile_assist.port.llm.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromptParsingExceptionTest {

  @DisplayName("Constructor with message returns the supplied value")
  @Test
  void constructor_message() {
    var message = "Test Message";
    var exception = new PromptParsingException(message);
    assertThat(exception.getMessage()).isEqualTo(message);
  }

  @DisplayName("Constructor with message and cause returns the supplied values")
  @Test
  void constructor_messageAndCause() {
    var message = "Test Message";
    var cause = new IllegalArgumentException();
    var exception = new PromptParsingException(message, cause);
    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getCause()).isEqualTo(cause);
  }
}
