package com.thesis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadPromptServiceTest {
  private final String PROJECT_DESCRIPTION = "This is a project description";
  private final String LONG_ARTICLE_OPTION = "longArticle";
  private final String ENGLISH_LANGUAGE = "english";


  private ReadPromptService readPromptService;

  @BeforeEach
  void setUp() {
    readPromptService = new ReadPromptService(new TextFileService());
  }

  @Test
  void testTwittorFormatOption() {
    String twitterOption = "twitter";
    List<String> prompts = readPromptService.prompts(PROJECT_DESCRIPTION, twitterOption, ENGLISH_LANGUAGE);

    assertEquals(3, prompts.size());

    assertEquals("This is a project description", prompts.get(0));
    assertEquals("Generate the content in twitter format.", prompts.get(1));
    assertEquals("Generate the output in HTML format.", prompts.get(2));
  }

  @Test
  void testAbstractFormatOption() {

    String abstractOption = "abstract";
    List<String> prompts = readPromptService.prompts(PROJECT_DESCRIPTION, abstractOption, ENGLISH_LANGUAGE);

    assertEquals(3, prompts.size());

    assertEquals("This is a project description", prompts.get(0));
    assertEquals("write an abstract description of the article about the project of the development company Iteratec with the customer.", prompts.get(1));
    assertEquals("Generate the output in HTML format.", prompts.get(2));
  }

  @Test
  void testLongArticleFormatOption() {

    List<String> prompts = readPromptService.prompts(PROJECT_DESCRIPTION, LONG_ARTICLE_OPTION, ENGLISH_LANGUAGE);

    assertEquals(3, prompts.size());

    assertEquals("This is a project description", prompts.get(0));
    assertThat(prompts.get(1)).containsAnyOf("I would like you to generate a long article");
    assertEquals("Generate the output in HTML format.", prompts.get(2));
  }

  @Test
  void testGermanLanguageOption() {

    String germanLanguage = "german";
    List<String> prompts = readPromptService.prompts(PROJECT_DESCRIPTION, LONG_ARTICLE_OPTION, germanLanguage);

    assertEquals(4, prompts.size());

    assertEquals("Generate the article in german.", prompts.get(2));
  }
}