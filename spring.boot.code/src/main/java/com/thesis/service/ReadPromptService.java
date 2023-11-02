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

        // read prompts zero shot prompt
        String promptFilePath = Path.of(BASE_PATH, "zeroShot", "prompt.txt").toString();

        // read prompts one shot prompt
       // String promptFilePath = Path.of(BASE_PATH, "oneShot", "prompt.txt").toString();

        // read prompts two shot prompt
       // String promptFilePath = Path.of(BASE_PATH, "twoShot", "prompt.txt").toString();

        String twittorPrompt = "Generate the content in twitter format.";
        String abstractPrompt = "write an abstract description of the article about the project of the development company Iteratec with the customer.";
        String longArticlePrompt = textFileService.readTextFile(promptFilePath);
        String germanPrompt = "Generate the article in german.";
        String additionalPrompt = "Generate the output in HTML format.";

        prompts.add(projectDescription);

        switch (option) {
            case "twitter" ->
                    prompts.add(twittorPrompt);
            case "abstract" ->
                    prompts.add(abstractPrompt);
            case "longArticle" ->
                    prompts.add(longArticlePrompt);
        }

        if (Objects.equals(language, "german"))
            prompts.add(germanPrompt);

        prompts.add(additionalPrompt);

        return prompts;
    }

}