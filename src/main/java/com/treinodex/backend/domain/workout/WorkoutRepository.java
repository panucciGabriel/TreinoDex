package com.treinodex.backend.domain.workout;

import com.treinodex.backend.domain.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID> {

    List<Workout> findAllByStudent(Student student);
}
