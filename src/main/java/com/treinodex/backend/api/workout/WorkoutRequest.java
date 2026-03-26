package com.treinodex.backend.api.workout;

public record WorkoutRequest(String name, String description, Boolean active) {
}
