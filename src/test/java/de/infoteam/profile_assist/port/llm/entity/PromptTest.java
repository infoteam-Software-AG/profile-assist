/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.port.llm.entity;

import static de.infoteam.profile_assist.port.llm.entity.Prompt.RESOURCE_NOT_FOUND_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.infoteam.profile_assist.port.llm.integration.PromptParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromptTest {

  @DisplayName("When the given resource does not exist, get throws the appropriate exception")
  @Test
  void get_throws_whenResourceDoesNotExist() {
    var resource = "doesNotExist";
    var prompt = new Prompt(resource);

    assertThatThrownBy(prompt::get)
        .isInstanceOf(PromptParsingException.class)
        .hasMessage(String.format(RESOURCE_NOT_FOUND_MESSAGE, resource));
  }
}
