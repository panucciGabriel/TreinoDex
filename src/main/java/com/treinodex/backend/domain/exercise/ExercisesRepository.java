package com.treinodex.backend.domain.exercise;

import com.treinodex.backend.domain.workout.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExercisesRepository extends JpaRepository<Exercise, UUID> {

    Page<Exercise> findAllByWorkout(Workout workout, Pageable pageable);
}
