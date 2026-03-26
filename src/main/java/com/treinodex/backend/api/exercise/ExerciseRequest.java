package com.treinodex.backend.api.exercise;

public record ExerciseRequest(
        String name,
        int sets,
        String reps,
        String rest,
        String weight
){
}
