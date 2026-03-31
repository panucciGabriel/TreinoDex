package com.treinodex.backend.api.exercise;

import com.treinodex.backend.domain.exercise.Exercise;
import com.treinodex.backend.domain.exercise.ExercisesRepository;
import com.treinodex.backend.domain.user.User;
import com.treinodex.backend.domain.workout.Workout;
import com.treinodex.backend.domain.workout.WorkoutRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExercisesRepository exercisesRepository;
    private final WorkoutRepository workoutRepository;

    public ExerciseController(ExercisesRepository exercisesRepository, WorkoutRepository workoutRepository) {
        this.exercisesRepository = exercisesRepository;
        this.workoutRepository = workoutRepository;
    }

    @PostMapping("/workout/{workoutId}")
    public ResponseEntity<ExerciseResponse> createExercise(@PathVariable UUID workoutId, @RequestBody @Valid ExerciseRequest request, @AuthenticationPrincipal User loggedPersonal) {

        var workoutOptional = workoutRepository.findById(workoutId);

        if (workoutOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Workout workout = workoutOptional.get();

        var tokenOwner = workout.getStudent().getPersonalTrainer().getId();

        if (!tokenOwner.equals(loggedPersonal.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Exercise exercise = new Exercise();

        exercise.setName(request.name());
        exercise.setSets(request.sets());
        exercise.setReps(request.reps());
        exercise.setRest(request.rest());
        exercise.setWeight(request.weight());

        exercise.setWorkout(workout);

        exercisesRepository.save(exercise);

        return ResponseEntity.ok(new ExerciseResponse(exercise));
    }

    @GetMapping("/workout/{workoutId}")
    public ResponseEntity<Page<ExerciseResponse>> listExercise(@PathVariable UUID workoutId, @AuthenticationPrincipal User loggedPersonal, @PageableDefault(size = 10, sort = "name") Pageable pageable) {

        var workoutOptional = workoutRepository.findById(workoutId);

        if (workoutOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Workout workout = workoutOptional.get();

        var tokenOwner = workout.getStudent().getPersonalTrainer().getId();

        if (!tokenOwner.equals(loggedPersonal.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Page<Exercise> exercises = exercisesRepository.findAllByWorkout(workout, pageable);

        Page<ExerciseResponse> response = exercises.map(ExerciseResponse::new);

        return ResponseEntity.ok(response);
    }
}
