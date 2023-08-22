package com.thesis.service;

import com.thesis.config.OpenAIRestTemplateConfig;
import com.thesis.dto.ChatRequest;
import com.thesis.dto.ChatResponse;
import com.thesis.dto.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatGptCommunicationService {

    private final RestTemplate restTemplate;
    private final OpenAIRestTemplateConfig openAIRestTemplateConfig;

    public String apiCall(List<String> prompts) {
        ChatRequest request = new ChatRequest(openAIRestTemplateConfig.getModel(), 0);
        List<Message> messages = new ArrayList<>();
        for (String prompt : prompts) {
            messages.add(new Message("user", prompt));
        }
        request.setMessages(messages);
        restTemplate.postForObject(openAIRestTemplateConfig.getApiUrl(), request, ChatResponse.class);
        ChatResponse chatResponse = restTemplate.postForObject(openAIRestTemplateConfig.getApiUrl(), request, ChatResponse.class);

        if (chatResponse == null || chatResponse.getChoices() == null || chatResponse.getChoices().isEmpty()) {
            return "No response";
        }

        String response = chatResponse.getChoices().get(0).getMessage().getContent();

        // write output to a text file
        String outputPath = "src/main/resources/output/APPzumARZT/outputArticle.txt";
        try {
            writeTextFile(outputPath, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private static void writeTextFile(String filePath, String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        }
    }
}
