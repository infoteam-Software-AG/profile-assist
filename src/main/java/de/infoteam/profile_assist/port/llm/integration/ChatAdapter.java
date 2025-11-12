package de.infoteam.profile_assist.port.llm.integration;

import de.infoteam.profile_assist.domain.entity.Persona;
import de.infoteam.profile_assist.port.llm.control.DateTimeTools;
import de.infoteam.profile_assist.port.llm.control.PromptForPersonaOptimizationPort;
import de.infoteam.profile_assist.port.llm.control.PromptForSimpleQuestionPort;
import de.infoteam.profile_assist.port.llm.control.PromptForTestPersonaPort;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChatAdapter
        implements PromptForSimpleQuestionPort,
        PromptForPersonaOptimizationPort,
        PromptForTestPersonaPort {

    private final ChatClient chatClient;

    public ChatAdapter(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String promptForSimpleQuestion(String question) {
        return this.chatClient.prompt().user(question).call().content();
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
                        "jobTitle", originPersona.getJobTitle(),
                        "skills", originPersona.getSkills(),
                        "projectHistory", originPersona.getProjectHistory());
        return createPersonaOptimizationPromptTemplate().render(variables);
    }

    private PromptTemplate createPersonaOptimizationPromptTemplate() {
        return PromptTemplate.builder()
                .renderer(
                        StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
                .template(
                        """
                                Persona details:
                                - Job title: <jobTitle>
                                - Skills: <skills>
                                - Project history: <projectHistory>
                                
                                Please provide:
                                1. Optimize and beautify project summaries
                                2. Add missing skills from projects to the skills list
                                3. Rename the job title to something meaningful.
                                4. Do not remove any information provided.
                                """)
                .build();
    }

    @Override
    public Persona promptForTestPersona() {
        return this.chatClient
                .prompt()
                .user("Fill the personas attribute with dummy values")
                .call()
                .entity(Persona.class);
    }

}
