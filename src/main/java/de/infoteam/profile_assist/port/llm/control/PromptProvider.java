/**
 * (C)2025 infoteam Software AG
 */

package de.infoteam.profile_assist.port.llm.control;

import de.infoteam.profile_assist.port.llm.entity.Prompt;

public interface PromptProvider {
  Prompt systemPrompt();

  Prompt userPrompt();
}
