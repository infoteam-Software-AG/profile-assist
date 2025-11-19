package de.infoteam.profile_assist.port.llm.control;

import de.infoteam.profile_assist.domain.control.OptimizeProjectDescriptionUseCase;
import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.domain.entity.Project;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringAiOptimizeProjectDescriptionUseCase
    implements OptimizeProjectDescriptionUseCase {

  private final ChatClient chatClient;

  public SpringAiOptimizeProjectDescriptionUseCase(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  @Override
  public Persona optimizeProject(Project project, String systemPrompt, String userPrompt) {
    return chatClient
        .prompt()
        .advisors(new SimpleLoggerAdvisor())
        .system(systemPrompt)
        .user(createPersonaOptimizationPrompt(project, userPrompt))
        .call()
        .entity(Persona.class);
  }

  private String createPersonaOptimizationPrompt(Project project, String promptTemplate) {
    Map<String, Object> variables = Map.of(/*"id", originPersona.id(),
            "jobTitle", originPersona.jobTitle(),
            "skills", originPersona.skills(),
            "certificates", originPersona.certificates(),
            "projectHistory", originPersona.projectHistory(),
            "startingDate", originPersona.startingDate(),
            "lastUpdate", originPersona.lastUpdate()*/ );
    return PromptTemplate.builder().template(promptTemplate).build().render(variables);
  }
}
