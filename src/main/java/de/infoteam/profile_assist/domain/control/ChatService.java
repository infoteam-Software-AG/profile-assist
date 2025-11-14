package de.infoteam.profile_assist.domain.control;

import de.infoteam.profile_assist.domain.entity.Persona;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final PromptForPersonaOptimizationUseCase promptForPersonaOptimizationUseCase;

  public Persona askForPersonaOptimization(Persona originPersona) {
    return promptForPersonaOptimizationUseCase.promptForPersonaOptimization(originPersona);
  }
}
