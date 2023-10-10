package com.thesis.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class WriteTextFileService {
    void writeTextFile(String filePath, String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        }
    }
}
