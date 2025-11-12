package de.infoteam.profile_assist.domain.control;

import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.port.llm.control.PromptForPersonaOptimizationPort;
import de.infoteam.profile_assist.port.llm.control.PromptForSimpleQuestionPort;
import de.infoteam.profile_assist.port.llm.control.PromptForTestPersonaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final PromptForSimpleQuestionPort promptForSimpleQuestionPort;
  private final PromptForPersonaOptimizationPort promptForPersonaOptimizationPort;
  private final PromptForTestPersonaPort promptForTestPersonaPort;

  public String askSimpleQuestion(String question) {
    return promptForSimpleQuestionPort.promptForSimpleQuestion(question);
  }

  public Persona askForPersonaOptimization(Persona originPersona) {
    return promptForPersonaOptimizationPort.promptForPersonaOptimization(originPersona);
  }

  public Persona askToCreateTestPersona() {
    return promptForTestPersonaPort.promptForTestPersona();
  }
}
