package de.infoteam.profile_assist.port.llm.integration;

public class PromptParsingException extends RuntimeException {
  public PromptParsingException(String message, Throwable cause) {
    super(message, cause);
  }
}
