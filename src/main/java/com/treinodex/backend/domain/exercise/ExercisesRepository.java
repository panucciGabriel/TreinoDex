package com.treinodex.backend.domain.exercise;

import com.treinodex.backend.domain.workout.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExercisesRepository extends JpaRepository<Exercise, UUID> {

    List<Exercise> findAllByWorkout(Workout workout);
}
