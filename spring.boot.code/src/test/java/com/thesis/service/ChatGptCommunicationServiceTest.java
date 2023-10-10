package com.thesis.service;

import com.thesis.config.OpenAIRestTemplateConfig;
import com.thesis.dto.ChatRequest;
import com.thesis.dto.ChatResponse;
import com.thesis.dto.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatGptCommunicationServiceTest {

  private ChatGptCommunicationService chatGptCommunicationService;

  private final RestTemplate restTemplate = mock(RestTemplate.class);

  private final OpenAIRestTemplateConfig openAIRestTemplateConfig = mock(OpenAIRestTemplateConfig.class);

  @BeforeEach
  void setUp() {
    chatGptCommunicationService = new ChatGptCommunicationService(restTemplate, openAIRestTemplateConfig);
  }

  @Test
  void testApiCall() {
    // Arrange
    List<String> prompts = new ArrayList<>();
    prompts.add("Prompt 1");
    prompts.add("Prompt 2");

    ChatResponse mockResponse = new ChatResponse();
    ChatResponse.Choice choice = new ChatResponse.Choice();
    choice.setMessage(new Message("assistant", "Response"));
    List<ChatResponse.Choice> choices = new ArrayList<>();
    choices.add(choice);
    mockResponse.setChoices(choices);

    when(openAIRestTemplateConfig.getModel()).thenReturn("model");
    when(openAIRestTemplateConfig.getApiUrl()).thenReturn("api-url");
    when(restTemplate.postForObject(eq("api-url"), any(ChatRequest.class), eq(ChatResponse.class)))
            .thenReturn(mockResponse);

    // Act
    String response = chatGptCommunicationService.callChatGPT(prompts);

    // Assert
    assertNotNull(response);
    assertEquals("Response", response);
  }
}
