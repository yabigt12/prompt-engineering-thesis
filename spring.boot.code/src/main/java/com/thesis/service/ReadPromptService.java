package com.thesis.service;

import java.nio.file.Path;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadPromptService {
    private final static String BASE_PATH = Path.of("src", "main", "resources", "Prompts").toString();


    public List<String> prompts(String option) {
        List<String> prompts = new ArrayList<>();

        // read prompts from text file
        String promptFilePath = Path.of(BASE_PATH, "zeroShot", "prompt.txt").toString();

        try {
            String prompt = readTextFile(promptFilePath);
            prompts.add(prompt);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String outputStyle = "";

        switch (option) {
            case "twitter":
                outputStyle = "Generate the article in twitter style.";
                break;
            case "abstract":
                outputStyle = "Generate an abstract of the article.";
                break;
            case "longArticle":
                break;
        }

        String additionalPrompt = "Generate the output in HTML format.";
        prompts.add(outputStyle);
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