package com.thesis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TextFileServiceTest {
  private static final String TEST_FILE_PATH = "test.txt";
  private static final String TEST_CONTENT = "Hello, this is a test!";
  private static final String INVALID_FILE_PATH = "/nonexistent/directory/test.txt";

  private TextFileService textFileService;

  @BeforeEach
  public void setUp() {
    textFileService = new TextFileService();
  }

  @Test
  public void testWriteTextFile() throws IOException {
    File file = createFile();

    textFileService.writeTextFile(TEST_FILE_PATH, TEST_CONTENT);

    String fileContent = Files.readString(file.toPath());

  assertEquals(TEST_CONTENT, fileContent);
  }

  @Test
  public void testReadTextFile() throws IOException {
    File file = createFile();
    Files.write(file.toPath(), TEST_CONTENT.getBytes());

    String fileContent = textFileService.readTextFile(TEST_FILE_PATH);

    assertEquals(TEST_CONTENT, fileContent.strip());
  }

  @Test
  public void testWriteTextFileWithInvalidPath() {
    assertThrows(RuntimeException.class, () -> {
      textFileService.writeTextFile(INVALID_FILE_PATH, TEST_CONTENT);
    });
  }

  @Test
  public void testReadTextFileWithInvalidPath() {
    assertThrows(RuntimeException.class, () -> {
      textFileService.readTextFile(INVALID_FILE_PATH);
    });
  }

  public File createFile() throws IOException {
    File file = new File(TEST_FILE_PATH);
    if (file.exists()) {
      file.delete(); // Delete the existing file if it exists
    }
    Files.createFile(file.toPath());

    return file;
  }

}