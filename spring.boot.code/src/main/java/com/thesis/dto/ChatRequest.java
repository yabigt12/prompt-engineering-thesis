package com.thesis.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private double temperature;

    public ChatRequest(String model, double temperature) {
        this.model = model;
        this.temperature = temperature;
    }
}
