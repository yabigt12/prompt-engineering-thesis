package com.thesis.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadPromptService {
    public List<String> prompts() {
        List<String> prompts = new ArrayList<>();

        // read prompts from text file
        String filePath = "src/main/resources/Prompts/zeroShot/prompt.txt";
        String filePath3 = "src/main/resources/Prompts/projectDescription1.txt";

        try {
            String prompt = readTextFile(filePath);
            String prompt2 = readTextFile(filePath3);
            prompts.add("[PROJECTINFORMATION]: " + prompt2);
            prompts.add(prompt);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String prompt3 = "Generate the output in HTML format.";

        prompts.add(prompt3);

        return prompts;
    }

    private String readTextFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return content.toString();
    }
}