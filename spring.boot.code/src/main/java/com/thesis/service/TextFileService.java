package com.thesis.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class TextFileService {
    void writeTextFile(String filePath, String content) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Error while writing to file", e);
        }
    }

    String readTextFile(String filePath) {
        StringBuilder content = new StringBuilder();

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while reading from file", e);
        }

        return content.toString();
    }
}
