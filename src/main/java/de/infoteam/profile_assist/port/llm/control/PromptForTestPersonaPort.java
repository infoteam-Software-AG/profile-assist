package de.infoteam.profile_assist.port.llm.control;

import de.infoteam.profile_assist.domain.entity.Persona;

public interface PromptForTestPersonaPort {

  Persona promptForTestPersona();
}
