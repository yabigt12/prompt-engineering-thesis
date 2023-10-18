package com.thesis.service;

import com.thesis.config.OpenAIRestTemplateConfig;
import com.thesis.dto.ChatRequest;
import com.thesis.dto.ChatResponse;
import com.thesis.dto.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ChatGptCommunicationService {

    private final RestTemplate restTemplate;
    private final OpenAIRestTemplateConfig openAIRestTemplateConfig;
    private final TextFileService writeAndReadTextFileService;

    public String callChatGPT(List<String> prompts) {
        ChatRequest request = new ChatRequest(openAIRestTemplateConfig.getModel(), 0);
        List<Message> messages = new ArrayList<>();

        System.out.println("Prompts: " + prompts);
        for (String prompt : prompts) {
            if(Objects.equals(prompt, prompts.get(0))) {
               // messages.add(new Message("system", prompt));
                continue;
            }
            messages.add(new Message("user", prompt));
        }

        System.out.println("Messages: " + messages);
        request.setMessages(messages);
        
        ChatResponse chatResponse = restTemplate.postForObject(openAIRestTemplateConfig.getApiUrl(), request, ChatResponse.class);

        if (chatResponse == null || chatResponse.getChoices() == null || chatResponse.getChoices().isEmpty()) {
            return "No response";
        }

        String content = chatResponse.getChoices().get(0).getMessage().getContent();

        // write output to a text file
        String outputPath = "src/main/resources/output/APPzumARZT/outputArticle.txt";
        writeAndReadTextFileService.writeTextFile(outputPath, content);

        return content;
    }
}
