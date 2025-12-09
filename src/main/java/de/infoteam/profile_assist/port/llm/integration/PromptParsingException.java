// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.integration;

public class PromptParsingException extends RuntimeException {

  public PromptParsingException(String message) {
    super(message);
  }

  public PromptParsingException(String message, Throwable cause) {
    super(message, cause);
  }
}
