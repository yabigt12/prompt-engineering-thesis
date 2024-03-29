package com.thesis.controller;

import com.thesis.dto.GenerateArticle;
import com.thesis.dto.GenerateArticleResponse;
import com.thesis.service.ChatGptCommunicationService;
import com.thesis.service.ReadPromptService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Validated
public class ChatController {

    private final ChatGptCommunicationService chatGptCommunicationService;

    private final ReadPromptService readPromptService;

    @PostMapping("/generateArticle")
    public GenerateArticleResponse generateArticle(@RequestBody GenerateArticle requestData) {
        List<String> prompts = readPromptService.prompts(requestData.getProjectDescription(),
                requestData.getOption(), requestData.getLanguage());
        String generatedArticle = chatGptCommunicationService.callChatGPT(prompts);

        GenerateArticleResponse response = new GenerateArticleResponse();
        response.setArticle(generatedArticle);
        return response;
    }
}
