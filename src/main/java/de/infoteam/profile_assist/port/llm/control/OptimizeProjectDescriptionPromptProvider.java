package de.infoteam.profile_assist.port.llm.control;

import de.infoteam.profile_assist.port.llm.entity.Prompt;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OptimizeProjectDescriptionPromptProvider implements PromptProvider {

  private final Prompt systemPrompt = new Prompt("prompts/project-description/system-prompt.txt");
  private final Prompt userPrompt = new Prompt("prompts/project-description/user-prompt.txt");

  @Override
  public Prompt systemPrompt() {
    return systemPrompt;
  }

  @Override
  public Prompt userPrompt() {
    return userPrompt;
  }
}
