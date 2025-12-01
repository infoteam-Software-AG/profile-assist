package de.infoteam.profile_assist.domain.control;

import de.infoteam.profile_assist.domain.entity.OptimizationResult;
import de.infoteam.profile_assist.domain.entity.Project;

public interface OptimizeProjectDescriptionUseCase {
  OptimizationResult<Project> optimizeProjectDescription(Project project);
}
