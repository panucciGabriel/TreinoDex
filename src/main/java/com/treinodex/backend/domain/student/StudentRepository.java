package com.treinodex.backend.domain.student;

import com.treinodex.backend.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    Page<Student> findAllByPersonalTrainer(User personalTrainer, Pageable pageable);

    Optional<Student> findByIdAndPersonalTrainer(UUID id, User personal);
}
