package com.thesis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateArticle {

    @NotNull
    private String projectDescription;

    @NotNull
    private String option;

    @NotNull
    private String language;
}
