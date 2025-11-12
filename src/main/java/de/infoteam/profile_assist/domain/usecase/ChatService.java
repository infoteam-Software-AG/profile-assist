package de.infoteam.profile_assist.domain.usecase;

import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.domain.port.out.chat.PromptForPersonaOptimizationPort;
import de.infoteam.profile_assist.domain.port.out.chat.PromptForSimpleQuestionPort;
import de.infoteam.profile_assist.domain.port.out.chat.PromptForTestPersonaPort;
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
