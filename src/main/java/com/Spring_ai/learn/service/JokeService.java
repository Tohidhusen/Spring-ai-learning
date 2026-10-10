package com.Spring_ai.learn.service;


import com.Spring_ai.learn.tool.ShareMarket;
import lombok.RequiredArgsConstructor;

import org.apache.catalina.User;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;

import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JokeService {

    private final ChatClient chatClient;
    private final ShareMarket shareMarket;

    @Value("classpath:policy.pdf")
    private Resource policyFile;

private final VectorStore vectorStore;
    String systemPrompt= """
            you are Ai poet and you are here to give some you peom 
            ontopic:{topic} in language:{language}
            """;

    public String Joke(String topic, String lang){

        PromptTemplate template = new PromptTemplate(systemPrompt);
        String render=template.render(Map.of("topic",topic,"language",lang));
        return chatClient.prompt()
                .user(render)
                .call()
                .content();
    }
    public void injestPdfToVectorStore(){
        PagePdfDocumentReader reader =
                new PagePdfDocumentReader(
                        policyFile
                );

        List<Document> documents = reader.get();
        TokenTextSplitter splitter=TokenTextSplitter.builder().withChunkSize(200).build();
        List<Document> chunks=splitter.apply(documents);

        vectorStore.add(chunks);
    }
    public String basicRag(String message){
        String template= """
                You are a helpful assistant that provides information based on the following context:
                context:
                 {context}
                """;

        PromptTemplate template1 = new PromptTemplate(template);
        List<Document> documents=vectorStore.similaritySearch(SearchRequest.builder()
                        .query(message).topK(2).filterExpression("filename=='policy.pdf'")
                .build());
        String context =documents.stream().map(Document::getText).collect(Collectors.joining("\n\n"));
         String sysprompt=template1.render(Map.of("context",context));
        return chatClient.prompt().system(sysprompt).call().content();
    }

    public String buyshareIfitsInBudget(String message){
        return chatClient.prompt().user(message)
                .tools(shareMarket)
                .call().content();
    }


}
