package de.infoteam.profile_assist.domain.control;

import de.infoteam.profile_assist.domain.entity.Persona;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final OptimizePersonaUseCase optimizePersonaUseCase;

  public Persona askForPersonaOptimization(
      Persona originPersona, String systemPrompt, String userPrompt) {
    return optimizePersonaUseCase.optimizePersona(originPersona, systemPrompt, userPrompt);
  }
}
