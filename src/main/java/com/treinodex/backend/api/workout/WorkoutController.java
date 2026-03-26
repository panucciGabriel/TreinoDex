package com.treinodex.backend.api.workout;

import com.treinodex.backend.domain.student.Student;
import com.treinodex.backend.domain.student.StudentRepository;
import com.treinodex.backend.domain.user.User;
import com.treinodex.backend.domain.workout.Workout;
import com.treinodex.backend.domain.workout.WorkoutRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutRepository workoutRepository;
    private final StudentRepository studentRepository;

    public WorkoutController(WorkoutRepository workoutRepository, StudentRepository studentRepository) {
        this.workoutRepository = workoutRepository;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/student/{studentId}")
    public ResponseEntity<WorkoutResponse> createWorkout(@PathVariable UUID studentId, @RequestBody WorkoutRequest request, @AuthenticationPrincipal User loggedPersonal) {

        var studenOptional = studentRepository.findByIdAndPersonalTrainer(studentId, loggedPersonal);

        if  (studenOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = studenOptional.get();

        Workout workout = new Workout();

        workout.setName(request.name());
        workout.setDescription(request.description());
        workout.setActive(request.active());

        workout.setStudent(student);

        workoutRepository.save(workout);

        return ResponseEntity.ok(new WorkoutResponse(workout));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<WorkoutResponse>> listWorkouts(@PathVariable UUID studentId, @AuthenticationPrincipal User loggedPersonal) {

        var studenOptional = studentRepository.findByIdAndPersonalTrainer(studentId, loggedPersonal);

        if (studenOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var student = studenOptional.get();

        List<Workout> workouts = workoutRepository.findAllByStudent(student);

        List<WorkoutResponse> response = workouts.stream()
                .map(WorkoutResponse::new)
                .toList();

        return ResponseEntity.ok(response);
    }
}
