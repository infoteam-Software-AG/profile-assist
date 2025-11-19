package de.infoteam.profile_assist.domain.control;

import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.domain.entity.Project;

public interface OptimizeProjectDescriptionUseCase {

  // TODO: create a data object which contains the prompts
  Persona optimizeProject(Project project, String systemPrompt, String userPrompt);
}
