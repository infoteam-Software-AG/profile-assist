package de.infoteam.profile_assist.port.llm.control;

import de.infoteam.profile_assist.domain.control.OptimizeProjectDescriptionUseCase;
import de.infoteam.profile_assist.domain.entity.OptimizationResult;
import de.infoteam.profile_assist.domain.entity.Project;
import de.infoteam.profile_assist.port.llm.integration.SpringAiClient;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpringAiOptimizeProjectDescriptionUseCase
    implements OptimizeProjectDescriptionUseCase {

  private final SpringAiClient springAiClient;
  private final OptimizeProjectDescriptionPromptProvider promptProvider;

  @Override
  public OptimizationResult<Project> optimizeProjectDescription(Project project) {
    return springAiClient.sendPrompt(
        Project.class,
        promptProvider.systemPrompt().get(),
        promptProvider
            .userPrompt()
            .withVariables(
                () ->
                    Map.of(
                        "name", project.name(),
                        "description", project.description(),
                        "technologies", project.technologies())));
  }
}
