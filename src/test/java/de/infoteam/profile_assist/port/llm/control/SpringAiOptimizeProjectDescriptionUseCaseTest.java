// SPDX-FileCopyrightText: 2025 infoteam Software AG
// SPDX-License-Identifier: Apache-2.0
// For full license text see: https://github.com/infoteam-Software-AG/profile-assist/blob/main/LICENSE
package de.infoteam.profile_assist.port.llm.control;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import de.infoteam.profile_assist.domain.entity.OptimizationResult;
import de.infoteam.profile_assist.domain.entity.Project;
import de.infoteam.profile_assist.port.llm.entity.OptimizationResultImpl;
import de.infoteam.profile_assist.port.llm.integration.SpringAiClient;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpringAiOptimizeProjectDescriptionUseCaseTest {

  @InjectMocks SpringAiOptimizeProjectDescriptionUseCase springAiOptimizeProjectDescriptionUseCase;

  @Spy
  @SuppressWarnings("unused") // required for @InjectMocks
  OptimizeProjectDescriptionPromptProvider promptProvider =
      new OptimizeProjectDescriptionPromptProvider();

  @Mock SpringAiClient springAiClient;

  @Test
  @DisplayName("OptimizeProjectDescription should return correct Project")
  void testOptimizeProjectDescription() {
    var optimizedProject = projectBuilder().build();
    when(springAiClient.sendPrompt(eq(Project.class), anyString(), anyString(), anyString()))
        .thenReturn(new OptimizationResultImpl<>(optimizedProject));

    OptimizationResult<Project> actual =
        springAiOptimizeProjectDescriptionUseCase.optimizeProjectDescription(
            optimizedProject.toBuilder().description("unoptimized description").build(), "", "");

    then(actual.result()).isEqualTo(optimizedProject);
  }

  private static Project.ProjectBuilder projectBuilder() {
    return Project.builder()
        .name("name")
        .description("description")
        .technologies(Collections.emptyList())
        .timePeriod("timePeriod")
        .businessSector("industry")
        .teamSize(3)
        .role("Senior Developer")
        .specializedFocus("focus")
        .methodologies(Collections.emptyList())
        .personalContributions(Collections.emptyList());
  }
}
