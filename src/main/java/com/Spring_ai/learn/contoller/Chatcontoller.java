package com.Spring_ai.learn.contoller;

import com.Spring_ai.learn.DTO.PoemResponse;
import com.Spring_ai.learn.service.JokeService;
import com.Spring_ai.learn.tool.RouterTool;
import lombok.RequiredArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class Chatcontoller {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final JokeService jokeService;
    private final RouterTool routerTools;


    @GetMapping("/poem")
    public String getpoem(@RequestParam String topic, @RequestParam String lang) {
          return chatClient.prompt()
                  .user("Write a poem about " + topic + " in " + lang + " language. Please provide the title, the poem text, and the rhyme scheme.")
                  .call()
                  .content();

    }
    @GetMapping("/match-vibe")
    public String getVibeMatcherSong(@RequestParam String feeling) {
        return chatClient.prompt()
                .user("Find a song that matches the feeling of " + feeling + ". Please provide the song title, artist, and a brief description of why it matches the feeling.")
                .advisors(advisor -> advisor.param(
                                ChatMemory.CONVERSATION_ID,
                                "user-123"))
                .call()
                .content();
    }
    @PostMapping("/chat")
    public String Question(@RequestBody String question) {
        return jokeService.basicRag(question);
    }
    @PostMapping("/support")
    public String chat(@RequestBody SupportRequest request) {
        return chatClient.prompt()
                .user(request.message())
                .tools(routerTools)
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        request.conversationId()
                ))
                .call()
                .content();
    }

    public record SupportRequest(
            String conversationId,
            String message
    ) {}
}
