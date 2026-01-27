// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.control;

import de.infoteam.profile_assist.domain.control.OptimizeProjectUseCase;
import de.infoteam.profile_assist.domain.entity.OptimizationResult;
import de.infoteam.profile_assist.domain.entity.Project;
import de.infoteam.profile_assist.port.llm.entity.OptimizationResultImpl;
import de.infoteam.profile_assist.port.llm.integration.SpringAiClient;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpringAiOptimizeProjectUseCase
    implements OptimizeProjectUseCase {

  private final SpringAiClient springAiClient;
  private final OptimizeProjectDescriptionPromptProvider promptProvider;

  @Override
  public OptimizationResult<Project> optimizeProjectDescription(
      Project project, String bidProjectDescription) {
    var result = springAiClient.sendPrompt(
        Project.class,
        promptProvider.systemPrompt().get(),
        promptProvider
            .userPrompt()
            .withVariables(
                () ->
                    Map.of(
                        "bidProjectDescription", bidProjectDescription,
                        "name", project.name(),
                        "description", project.description(),
                        "technologies", project.technologies())));

    return new OptimizationResultImpl<>(project.toBuilder()
      .description(result.result().description())
      .technologies(result.result().technologies())
      .personalContributions(result.result().personalContributions())
      .methodologies(result.result().methodologies())
      .specializedFocus(result.result().specializedFocus())
      .build());
  }
}
