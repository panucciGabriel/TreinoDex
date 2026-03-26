package com.treinodex.backend.api.student;

import com.treinodex.backend.domain.student.Student;

import java.util.UUID;

public record StudentResponse(UUID id, String name, String email, String phone) {

    public StudentResponse(Student student) {
        this(student.getId(), student.getName(), student.getEmail(), student.getPhone());
    }
}
