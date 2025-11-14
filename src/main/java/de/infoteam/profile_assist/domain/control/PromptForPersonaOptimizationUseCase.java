package de.infoteam.profile_assist.domain.control;

import de.infoteam.profile_assist.domain.entity.Persona;

public interface PromptForPersonaOptimizationUseCase {

  Persona promptForPersonaOptimization(Persona originPersona);
}
