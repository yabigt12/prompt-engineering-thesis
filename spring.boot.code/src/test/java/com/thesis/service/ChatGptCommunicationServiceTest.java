package com.thesis.service;

import com.thesis.config.OpenAIRestTemplateConfig;
import com.thesis.dto.ChatRequest;
import com.thesis.dto.ChatResponse;
import com.thesis.dto.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatGptCommunicationServiceTest {

  private ChatGptCommunicationService chatGptCommunicationService;

  private final RestTemplate restTemplate = mock(RestTemplate.class);

  private final OpenAIRestTemplateConfig openAIRestTemplateConfig = mock(OpenAIRestTemplateConfig.class);

  private final TextFileService textFileService = mock(TextFileService.class);

  @BeforeEach
  void setUp() {
    chatGptCommunicationService = new ChatGptCommunicationService(restTemplate, openAIRestTemplateConfig, textFileService);
  }

  @Test
  void testCallChatGPT() {
    // Arrange
    List<String> prompts = Arrays.asList("Prompt 1", "Prompt 2");

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

  @Test
  public void testCallChatGPTWhenApiCallFails() {
    List<String> prompts = Arrays.asList("Prompt 1", "Prompt 2");

    ChatRequest expectedRequest = new ChatRequest("model", 0);
    List<Message> expectedMessages = Arrays.asList(new Message("user", "Prompt 1"), new Message("user", "Prompt 2"));
    expectedRequest.setMessages(expectedMessages);

    when(openAIRestTemplateConfig.getApiUrl()).thenReturn("apiUrl");
    when(openAIRestTemplateConfig.getModel()).thenReturn("model");
    when(restTemplate.postForObject("apiUrl", expectedRequest, ChatResponse.class)).thenReturn(null);

    String result = chatGptCommunicationService.callChatGPT(prompts);

    Mockito.verify(openAIRestTemplateConfig).getApiUrl();
    Mockito.verify(textFileService, Mockito.never()).writeTextFile(Mockito.anyString(), Mockito.anyString());
    assertEquals("No response", result);
  }
}
