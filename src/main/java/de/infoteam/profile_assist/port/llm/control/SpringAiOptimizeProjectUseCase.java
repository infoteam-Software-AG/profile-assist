// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.control;

import de.infoteam.profile_assist.domain.control.OptimizeProjectUseCase;
import de.infoteam.profile_assist.domain.entity.OptimizationResult;
import de.infoteam.profile_assist.domain.entity.Project;
import de.infoteam.profile_assist.domain.entity.Skills;
import de.infoteam.profile_assist.port.llm.entity.OptimizationResultImpl;
import de.infoteam.profile_assist.port.llm.integration.SpringAiClient;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpringAiOptimizeProjectUseCase implements OptimizeProjectUseCase {

  private final SpringAiClient springAiClient;
  private final OptimizeProjectDescriptionPromptProvider promptProvider;

  @Override
  public OptimizationResult<Project> optimizeProjectDescription(
      Project project, String bidProjectDescription) {
    var result =
        springAiClient.sendPrompt(
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

    return new OptimizationResultImpl<>(
        project.toBuilder()
            .description(result.result().description())
            .technologies(result.result().technologies())
            .personalContributions(result.result().personalContributions())
            .methodologies(result.result().methodologies())
            .specializedFocus(result.result().specializedFocus())
            .build());
  }

  @Override
  public OptimizationResult<Project> optimizeProjectDescriptionWithoutBid(Project project) {
    var result =
        springAiClient.sendPrompt(
            Project.class,
            promptProvider.systemPrompt().get(),
            promptProvider
                .userPromptWithoutBid()
                .withVariables(
                    () ->
                        Map.of(
                            "name",
                            project.name(),
                            "description",
                            project.description(),
                            "technologies",
                            project.technologies(),
                            "timePeriod",
                            project.timePeriod(),
                            "businessSector",
                            project.businessSector(),
                            "teamSize",
                            project.teamSize(),
                            "role",
                            project.role(),
                            "specializedFocus",
                            project.specializedFocus(),
                            "personalContributions",
                            project.personalContributions(),
                            "methodologies",
                            project.methodologies())));

    return new OptimizationResultImpl<>(
        project.toBuilder()
            .description(result.result().description())
            .technologies(result.result().technologies())
            .personalContributions(result.result().personalContributions())
            .methodologies(result.result().methodologies())
            .specializedFocus(result.result().specializedFocus())
            .build());
  }

  @Override
  public OptimizationResult<Project> optimizeProjectWithPersonaSkills(
      Skills skills, Project project) {
    var result =
        springAiClient.sendPrompt(
            Project.class,
            promptProvider.systemPrompt().get(),
            promptProvider
                .userSkillsMappingPrompt()
                .withVariables(
                    () ->
                        Map.ofEntries(
                            Map.entry("skillsBusinessSectors", skills.businessSectors()),
                            Map.entry("languages", skills.languages()),
                            Map.entry("programmingLanguages", skills.programmingLanguages()),
                            Map.entry("methodicalExpertise", skills.methodicalExpertise()),
                            Map.entry("tools", skills.tools()),
                            Map.entry("operatingSystems", skills.operatingSystems()),
                            Map.entry("databases", skills.databases()),
                            Map.entry("name", project.name()),
                            Map.entry("description", project.description()),
                            Map.entry("technologies", project.technologies()),
                            Map.entry("timePeriod", project.timePeriod()),
                            Map.entry("businessSector", project.businessSector()),
                            Map.entry("teamSize", project.teamSize()),
                            Map.entry("role", project.role()),
                            Map.entry("specializedFocus", project.specializedFocus()),
                            Map.entry("personalContributions", project.personalContributions()),
                            Map.entry("methodologies", project.methodologies()))));
    return new OptimizationResultImpl<>(
        project.toBuilder()
            .description(result.result().description())
            .technologies(result.result().technologies())
            .personalContributions(result.result().personalContributions())
            .methodologies(result.result().methodologies())
            .specializedFocus(result.result().specializedFocus())
            .build());
  }
}
