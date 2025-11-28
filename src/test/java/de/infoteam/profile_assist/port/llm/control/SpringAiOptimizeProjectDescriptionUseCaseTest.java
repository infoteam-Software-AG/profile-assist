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
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpringAiOptimizeProjectDescriptionUseCaseTest {

  @InjectMocks SpringAiOptimizeProjectDescriptionUseCase springAiOptimizeProjectDescriptionUseCase;

  @Mock SpringAiClient springAiClient;

  @Test
  @DisplayName("OptimizeProjectDescription should return correct Project")
  void testOptimizeProjectDescription() {
    when(springAiClient.sendPrompt(eq(Project.class), anyString(), anyString()))
        .thenReturn(
            new OptimizationResultImpl<>(
                new Project("name", "description", Collections.emptyList())));

    OptimizationResult<Project> actual =
        springAiOptimizeProjectDescriptionUseCase.optimizeProjectDescription(
            new Project("name", "unoptimizedDescription", Collections.emptyList()));

    Project expected = new Project("name", "description", Collections.emptyList());
    then(actual.result()).isEqualTo(expected);
  }
}
