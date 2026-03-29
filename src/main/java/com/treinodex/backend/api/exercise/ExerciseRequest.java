package com.treinodex.backend.api.exercise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExerciseRequest(
        @NotBlank
        String name,
        @NotNull
        int sets,
        @NotBlank
        String reps,
        String rest,
        String weight
){
}
