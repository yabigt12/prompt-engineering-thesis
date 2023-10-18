package com.thesis.service;

import java.nio.file.Path;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ReadPromptService {
    private final TextFileService textFileService;
    private final static String BASE_PATH = Path.of("src", "main", "resources", "Prompts").toString();

    public List<String> prompts(String projectDescription, String option, String language) {
        List<String> prompts = new ArrayList<>();

        // read prompts from text file
        String promptFilePath = Path.of(BASE_PATH, "zeroShot", "prompt.txt").toString();
        String systemMessageFilePath = Path.of(BASE_PATH, "systemMessage.txt").toString();

        String outputStyle = "";
        String selectedLanguage = "";

        String systemMessage = textFileService.readTextFile(systemMessageFilePath);
        prompts.add(systemMessage);

        prompts.add(projectDescription);

        switch (option) {
            case "twitter" ->
                    outputStyle = "Generate the content in twitter format.";
            case "abstract" ->
                    outputStyle = "write an abstract description of the article about the project of the development company Iteratec with the customer.";
            case "longArticle" ->
                    outputStyle = textFileService.readTextFile(promptFilePath);
        }

        prompts.add(outputStyle);

        if (Objects.equals(language, "german"))
            selectedLanguage = "generate the article in german";

        String additionalPrompt = "Generate the output in HTML format.";
        prompts.add(selectedLanguage);
        prompts.add(additionalPrompt);

        return prompts;
    }

}