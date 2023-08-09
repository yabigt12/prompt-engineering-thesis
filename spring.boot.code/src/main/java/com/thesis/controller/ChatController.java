package com.thesis.controller;

import com.thesis.dto.ChatRequest;
import com.thesis.dto.ChatResponse;
import com.thesis.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;

import java.util.List;

@RestController
public class ChatController {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @GetMapping("/chat")
    public String chat() {

        List<String> prompts = new ArrayList<>();


        // read prompts from text file
        String filePath = "src/main/resources/Prompts/zeroShot/prompt.txt";
        String filePath3 = "src/main/resources/Prompts/projectDescription1.txt";

        try {
            String prompt = readTextFile(filePath);
            String prompt2 = readTextFile(filePath3);
            prompts.add("[PROJECTINFORMATION]: " + prompt2);
            prompts.add(prompt);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String prompt3 = "Generate the output in HTML format.";

        prompts.add(prompt3);

        // create a request
        ChatRequest request = new ChatRequest(model, 0);
        List<Message> messages = new ArrayList<>();
        for (String prompt : prompts) {
            messages.add(new Message("user", prompt));
        }
        request.setMessages(messages);
        // call the API
        restTemplate.postForObject(apiUrl, request, ChatResponse.class);
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // return the first response
        String chatResponse = response.getChoices().get(0).getMessage().getContent();

        // write output to a text file
        String outputPath = "C:\\Users\\yteferra\\Downloads\\spring.boot.code\\spring.boot.code\\src\\main\\resources\\output\\APPzumARZT\\outputArticle.txt";
        try {
            writeTextFile(outputPath, chatResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chatResponse;
    }

    public static String readTextFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return content.toString();
    }

    public void writeTextFile(String filePath, String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        }
    }
}
