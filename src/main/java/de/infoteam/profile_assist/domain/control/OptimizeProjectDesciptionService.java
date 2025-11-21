package de.infoteam.profile_assist.domain.control;

import de.infoteam.profile_assist.domain.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimizeProjectDesciptionService {
  private final OptimizeProjectDescriptionUseCase optimizePersonaUseCase;

  public Project optimizeProjectDescription(Project project) {
    return optimizePersonaUseCase.optimizeProjectDescription(project);
  }
}
