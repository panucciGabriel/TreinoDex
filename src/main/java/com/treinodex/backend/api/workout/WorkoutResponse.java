package com.treinodex.backend.api.workout;

import com.treinodex.backend.domain.workout.Workout;

import java.util.UUID;

public record WorkoutResponse(UUID id, String name, String description, Boolean active) {

    public WorkoutResponse(Workout workout) {
        this(workout.getId(), workout.getName(), workout.getDescription(), workout.getActive());
    }
}
