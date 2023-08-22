package com.thesis.controller;

import com.thesis.service.ChatGptCommunicationService;
import com.thesis.service.ReadPromptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ChatController {

    private final ChatGptCommunicationService chatGptCommunicationService;

    private final ReadPromptService readPromptService;

    @GetMapping("/chat")
    public String chat() {
        List<String> prompts = readPromptService.prompts();
        return chatGptCommunicationService.apiCall(prompts);
    }
}
