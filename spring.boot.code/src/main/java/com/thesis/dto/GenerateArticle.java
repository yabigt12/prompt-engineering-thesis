package com.thesis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GenerateArticle {

    @NotNull
    private String projectDescription;

    @NotNull
    private String option;

    @NotNull
    private String language;
}
