package com.thesis.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReadPromptService {
    private final static String BASE_PATH = Path.of("src", "main", "resources", "Prompts").toString();


    public List<String> prompts(String projectDescription, String option, String language) {
        List<String> prompts = new ArrayList<>();

        // read prompts from text file
        String promptFilePath = Path.of(BASE_PATH, "zeroShot", "prompt.txt").toString();
        String systemMessageFilePath = Path.of(BASE_PATH, "systemMessage.txt").toString();

        String outputStyle = "";
        String selectedLanguage = "";

        try {
            String systemMessage = readTextFile(systemMessageFilePath);
            prompts.add(systemMessage);

            prompts.add(projectDescription);

            switch (option) {
                case "twitter" ->
                        outputStyle = "Generate the content in twitter format.";
                case "abstract" ->
                        outputStyle = "write an abstract description of the article about the project of the development company Iteratec with the customer.";
                case "longArticle" ->
                        outputStyle = readTextFile(promptFilePath);
            }

            prompts.add(outputStyle);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Objects.equals(language, "german"))
            selectedLanguage = "generate the article in german";

        String additionalPrompt = "Generate the output in HTML format.";
        prompts.add(selectedLanguage);
        prompts.add(additionalPrompt);

        return prompts;
    }

    String readTextFile(String filePath) throws IOException {
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