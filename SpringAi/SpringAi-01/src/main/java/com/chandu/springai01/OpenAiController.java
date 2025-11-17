package com.chandu.springai01;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
public class OpenAiController {


    private ChatClient chatClient;

    public OpenAiController(OpenAiChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }

//    ChatMemory chatMemory = MessageWindowChatMemory.builder().build();
//
//    public OpenAiController(ChatClient.Builder builder) {
//        this.chatClient = builder
//                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
//                .build();
//
//
//    }

    @GetMapping("/response/{message}")
    public ResponseEntity<String> response(@PathVariable String message){
        ChatResponse chatResponse = chatClient
                .prompt(message)
                .call()
                .chatResponse();

        String ss = chatResponse.getMetadata().getModel();
        System.out.println(ss);

        String result = chatResponse
                .getResult()
                .getOutput()
                .getText();
        return ResponseEntity.ok(result);
    }


    @PostMapping("/recommend-movie")
    public String recommendAMovie(@RequestParam String type, @RequestParam String year, @RequestParam String lang){

        String tempt = """
                
                I want to watch a {type} movie released in {year} in {lang} language with good rating. 
                Suggest one specific one movie and tell me cast and movie plot in brief.
                 response in below format only:
                 
                    Movie Name: <movie name>
                    Cast: <cast names>
                    Plot: <brief plot>
                    language: <language>
                    Year: <release year>
                    IMDB Rating: <IMDB rating out of 10>
                """;


        PromptTemplate promptTemplate = new PromptTemplate(tempt);
        Prompt prompt = promptTemplate.create(Map.of("type", type,"year", year,
                "lang", lang));
        ChatResponse chatResponse = chatClient
                .prompt(prompt)
                .call()
                .chatResponse();

        String result = chatClient
                .prompt(prompt)
                .call()
                .content();
        return result;

    }
}


