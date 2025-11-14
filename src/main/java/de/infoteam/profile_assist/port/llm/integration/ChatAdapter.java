package de.infoteam.profile_assist.port.llm.integration;

import de.infoteam.profile_assist.domain.control.OptimizePersonaUseCase;
import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.port.llm.control.DateTimeTools;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatAdapter implements OptimizePersonaUseCase {

  private final ChatClient chatClient;

  public ChatAdapter(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  @Override
  public Persona optimizePersona(Persona originPersona, String systemPrompt, String userPrompt) {
    return chatClient
        .prompt()
        .advisors(new SimpleLoggerAdvisor())
        .system(systemPrompt)
        .user(createPersonaOptimizationPrompt(originPersona, userPrompt))
        .tools(new DateTimeTools())
        .call()
        .entity(Persona.class);
  }

  private String createPersonaOptimizationPrompt(Persona originPersona, String promptTemplate) {
    Map<String, Object> variables =
        Map.of(
            "id", originPersona.id(),
            "jobTitle", originPersona.jobTitle(),
            "skills", originPersona.skills(),
            "certificates", originPersona.certificates(),
            "projectHistory", originPersona.projectHistory(),
            "startingDate", originPersona.startingDate(),
            "lastUpdate", originPersona.lastUpdate());
    return PromptTemplate.builder().template(promptTemplate).build().render(variables);
  }
}
