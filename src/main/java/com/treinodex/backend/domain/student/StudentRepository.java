package com.treinodex.backend.domain.student;

import com.treinodex.backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    List<Student> findAllByPersonalTrainer(User personal);

    Optional<Student> findByIdAndPersonalTrainer(UUID id, User personal);
}
