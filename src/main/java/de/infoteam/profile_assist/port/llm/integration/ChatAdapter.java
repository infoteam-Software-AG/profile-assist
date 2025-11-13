package de.infoteam.profile_assist.port.llm.integration;

import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.port.llm.control.DateTimeTools;
import de.infoteam.profile_assist.port.llm.control.PromptForPersonaOptimizationPort;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.stereotype.Service;

@Service
public class ChatAdapter implements PromptForPersonaOptimizationPort {

  private final ChatClient chatClient;

  public ChatAdapter(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  @Override
  public Persona promptForPersonaOptimization(Persona originPersona) {
    return chatClient
        .prompt()
        .system("You are a project profile optimizer AI. Be professional, concise, and structured.")
        .user(createPersonaOptimizationPrompt(originPersona))
        .tools(new DateTimeTools())
        .call()
        .entity(Persona.class);
  }

  private String createPersonaOptimizationPrompt(Persona originPersona) {
    Map<String, Object> variables =
        Map.of(
            "id", originPersona.getId(),
            "jobTitle", originPersona.getJobTitle(),
            "skills", originPersona.getSkills(),
            "certificates", originPersona.getCertificates(),
            "projectHistory", originPersona.getProjectHistory(),
            "startingDate", originPersona.getStartingDate(),
            "lastUpdate", originPersona.getLastUpdate());
    return createPersonaOptimizationPromptTemplate().render(variables);
  }

  private PromptTemplate createPersonaOptimizationPromptTemplate() {
    return PromptTemplate.builder()
        .renderer(
            StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
        .template(
            """
                                Persona details:
                                - id: <id>
                                - Job title: <jobTitle>
                                - Skills: <skills>
                                - Certificates: <certificates>
                                - Project history: <projectHistory>
                                - Starting Date: <startingDate>
                                - Last Update: <lastUpdate>

                                Please provide:
                                1. Professionalize the project descriptions, if needed.
                                2. Add missing skills from projects to the skills list.
                                3. Rename the job title to something meaningful.
                                4. Do not remove any information provided.
                                5. Set the lastUpdate Date to the current date.
                                """)
        .build();
  }
}
