package com.treinodex.backend.api.exercise;

import com.treinodex.backend.domain.exercise.Exercise;

import java.util.UUID;

public record ExerciseResponse(UUID id, String name, int sets, String reps, String rest, String weight) {

    public ExerciseResponse(Exercise exercise) {
        this(exercise.getId(), exercise.getName(), exercise.getSets(), exercise.getReps(), exercise.getRest(), exercise.getWeight());
    }
}