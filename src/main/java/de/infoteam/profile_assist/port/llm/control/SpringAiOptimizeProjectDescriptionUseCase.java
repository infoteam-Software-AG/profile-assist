package de.infoteam.profile_assist.port.llm.control;

import de.infoteam.profile_assist.domain.control.OptimizeProjectDescriptionUseCase;
import de.infoteam.profile_assist.domain.entity.Project;
import de.infoteam.profile_assist.port.llm.integration.PromptParsingException;
import de.infoteam.profile_assist.port.llm.integration.SpringAiClient;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpringAiOptimizeProjectDescriptionUseCase
    implements OptimizeProjectDescriptionUseCase {

  private final SpringAiClient springAiClient;
  private final String systemPrompt;
  private final String userPrompt;

  public SpringAiOptimizeProjectDescriptionUseCase(SpringAiClient springAiClient) {
    this.springAiClient = springAiClient;
    try (InputStream systemPromptStream =
            getClass()
                .getClassLoader()
                .getResourceAsStream("prompts/project-description/system-prompt.txt");
        InputStream userPromptStream =
            getClass()
                .getClassLoader()
                .getResourceAsStream("prompts/project-description/user-prompt.txt"); ) {
      this.systemPrompt = new String(systemPromptStream.readAllBytes());
      this.userPrompt = new String(userPromptStream.readAllBytes());
    } catch (IOException | NullPointerException exception) {
      log.error("Error reading prompts", exception);
      throw new PromptParsingException("Error reading prompts", exception);
    }
  }

  @Override
  public Project optimizeProjectDescription(Project project) {
    var variables =
        Map.of(
            "name", project.name(),
            "description", project.description(),
            "technologies", project.technologies());
    var renderedUserPrompt =
        PromptTemplate.builder().template(userPrompt).build().render(variables);
    var result = springAiClient.sendPrompt(Project.class, systemPrompt, renderedUserPrompt);
    return result.result();
  }
}
