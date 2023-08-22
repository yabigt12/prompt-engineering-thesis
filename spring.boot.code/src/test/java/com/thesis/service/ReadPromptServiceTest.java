package com.thesis.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ReadPromptServiceTest {

  private ReadPromptService mockedReadPromptService = Mockito.spy(ReadPromptService.class);

  @SneakyThrows
  @Test
  void shouldReturn_whenPrompts() {
    doReturn("test").when(mockedReadPromptService).readTextFile(anyString()); // works with mock AND spy
    // when(mockedReadPromptService.readTextFile(anyString())).thenReturn("test");  // DOES NOT WORK WITH SPY

    List<String> prompts = mockedReadPromptService.prompts();

    assertThat(prompts).isNotNull();
    assertThat(prompts).hasSize(3);
    assertThat(prompts.get(1)).contains("[PROJECTINFORMATION]:");
    assertThat(prompts.get(2)).containsAnyOf("Generate", "output", "HTML");

    verify(mockedReadPromptService, times(2)).readTextFile(anyString());
  }

  @SneakyThrows
  @Test
  void shouldReturn_whenReadTextFile_givenValidPath() {
    String PATH = Path.of("src", "main", "resources", "Prompts", "zeroShot", "prompt.txt").toString();

    String result = mockedReadPromptService.readTextFile(PATH);

    assertThat(result).isNotNull();
  }

  @Test
  void shouldThrowException_whenReadTextFile_givenInvalidPath() {
    assertThrows(IOException.class,
      () -> mockedReadPromptService.readTextFile("_unknown path_"));
  }
}