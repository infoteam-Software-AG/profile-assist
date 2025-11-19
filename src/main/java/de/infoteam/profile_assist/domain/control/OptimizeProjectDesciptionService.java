package de.infoteam.profile_assist.domain.control;

import de.infoteam.profile_assist.domain.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimizeProjectDesciptionService {
  private final OptimizeProjectDescriptionUseCase optimizePersonaUseCase;

  // FIXME: define a reasonable return type
  public Object optimizeProjectDescription(
      Project project, String systemPrompt, String userPrompt) {
    return optimizePersonaUseCase.optimizeProject(project, systemPrompt, userPrompt);
  }
}
