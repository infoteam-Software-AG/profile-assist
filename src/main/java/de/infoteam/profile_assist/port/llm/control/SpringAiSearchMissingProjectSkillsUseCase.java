// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.control;

import de.infoteam.profile_assist.domain.control.OptimizeProjectUseCase;
import de.infoteam.profile_assist.domain.control.SearchMissingProjectSkillsUseCase;
import de.infoteam.profile_assist.domain.entity.OptimizationResult;
import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.domain.entity.Project;
import de.infoteam.profile_assist.port.llm.entity.OptimizationResultImpl;
import de.infoteam.profile_assist.port.llm.integration.SpringAiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpringAiSearchMissingProjectSkillsUseCase
    implements SearchMissingProjectSkillsUseCase {

  private final SpringAiClient springAiClient;
  private final OptimizeProjectDescriptionPromptProvider promptProvider;

  @Override
  public OptimizationResult<String> searchMissingProjectSkills(Persona persona) {
    OptimizationResultImpl<String> result =
        springAiClient.sendPrompt(
            String.class,
            promptProvider.systemPrompt().get(),
            promptProvider
                .userPromptSkills()
                .withVariables(
                    () ->
                        Map.of(
                            "skills", persona.skills(),
                            "projects", persona.projectHistory())));
    log.warn(
        "The Following Skills are in the Persona overview but not in a Project: {}, Persona: {}",
        result.result(),
        persona.name());
    return result;
  }
}
