package de.infoteam.profile_assist.domain.port.out.chat;

import de.infoteam.profile_assist.domain.entity.Persona;

public interface PromptForPersonaOptimizationPort {

    Persona promptForPersonaOptimization(Persona originPersona);

}
