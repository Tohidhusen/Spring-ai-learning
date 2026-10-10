package com.Spring_ai.learn;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static javax.xml.catalog.CatalogFeatures.builder;

@Configuration
public class ChatclientConfig {

    @Bean
    public ChatClient chatClient(  ChatModel chatModel,
                                   ChatMemory chatMemory,
                                   VectorStore vectorStore) {
        return ChatClient.builder(chatModel)
                .defaultSystem("""
                    You are a customer support assistant for routers.

                    For questions about setup, power, resetting,
                    and troubleshooting, use the user manual context.

                    When the user explicitly requests a router restart,
                    use the rebootRouter tool.

                    If the router serial number is unknown, ask for it.
                    Never invent a serial number.
                    Do not claim a router was restarted unless the
                    tool or router management service confirms it.
                    """)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore).build()
                )
                .build();
    }
    @Bean
    ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(3)
                .build();
    }
}
