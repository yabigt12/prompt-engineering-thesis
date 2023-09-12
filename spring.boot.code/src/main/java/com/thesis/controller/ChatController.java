package com.thesis.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
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
    public ResponseEntity<JsonNode> generateArticle(@RequestBody JsonNode requestData) {
        if (requestData.has("projectDescription") && requestData.has("option")) {
            String inputText = requestData.get("projectDescription").asText();
            String option = requestData.get("option").asText();
            List<String> prompts = readPromptService.prompts(option);
            prompts.add("[PROJECTINFORMATION]: <<" + inputText + " >>");
            String generatedArticle = chatGptCommunicationService.apiCall(prompts);
            return ResponseEntity.ok(JsonNodeFactory.instance.objectNode().put("article", generatedArticle));
        } else {
            return ResponseEntity.badRequest().body(JsonNodeFactory.instance.objectNode().put("error", "Invalid JSON structure"));
        }
    }
}
