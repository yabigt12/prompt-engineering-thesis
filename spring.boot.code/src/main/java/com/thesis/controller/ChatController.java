package com.thesis.controller;

import com.thesis.dto.GenerateArticle;
import com.thesis.dto.GenerateArticleResponse;
import com.thesis.service.ChatGptCommunicationService;
import com.thesis.service.ReadPromptService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ChatController {

    private final ChatGptCommunicationService chatGptCommunicationService;

    private final ReadPromptService readPromptService;

    @PostMapping("/generateArticle")
    public ResponseEntity<GenerateArticleResponse> generateArticle(@RequestBody GenerateArticle requestData) {
        if (requestData != null && requestData.getProjectDescription() != null
                && requestData.getOption() != null && requestData.getLanguage() != null) {
            List<String> prompts = readPromptService.prompts(requestData);
            String generatedArticle = chatGptCommunicationService.apiCall(prompts);

            GenerateArticleResponse response = new GenerateArticleResponse();
            response.setArticle(generatedArticle);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
