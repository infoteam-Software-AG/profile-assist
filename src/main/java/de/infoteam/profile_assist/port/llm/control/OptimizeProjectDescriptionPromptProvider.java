// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.control;

import de.infoteam.profile_assist.port.llm.entity.Prompt;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OptimizeProjectDescriptionPromptProvider implements PromptProvider {

  private final Prompt systemPrompt = new Prompt("prompts/project-description/system-prompt.txt");
  private final Prompt userPrompt = new Prompt("prompts/project-description/user-prompt.txt");
  private final Prompt userPromptSkills = new Prompt("prompts/skills/user-prompt.txt");

  @Override
  public Prompt systemPrompt() {
    return systemPrompt;
  }

  @Override
  public Prompt userPrompt() {
    return userPrompt;
  }

  @Override
  public Prompt userPromptSkills() {
    return userPromptSkills;
  }
}
