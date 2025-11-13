package de.infoteam.profile_assist.domain.control;

import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.port.llm.control.PromptForPersonaOptimizationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final PromptForPersonaOptimizationPort promptForPersonaOptimizationPort;

  public Persona askForPersonaOptimization(Persona originPersona) {
    return promptForPersonaOptimizationPort.promptForPersonaOptimization(originPersona);
  }
}
