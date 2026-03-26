package com.treinodex.backend.api.student;

import com.treinodex.backend.domain.student.*;
import com.treinodex.backend.domain.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository repository) {
        this.studentRepository = repository;
    }

    @PostMapping
    public ResponseEntity<?> createStudent (
            @RequestBody StudentRequest request,
            @AuthenticationPrincipal User loggedPersonal
    ) {
        Student newStudent = new Student();
        newStudent.setName(request.name());
        newStudent.setEmail(request.email());
        newStudent.setPhone(request.phone());
        newStudent.setPersonalTrainer(loggedPersonal);

        studentRepository.save(newStudent);

        return ResponseEntity.ok("Student created successfully" + loggedPersonal.getName());
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> listStudents(@AuthenticationPrincipal User loggedPersonal) {
        List<Student> students = studentRepository.findAllByPersonalTrainer(loggedPersonal);
        List<StudentResponse> response = students.stream()
                .map(StudentResponse::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id, @AuthenticationPrincipal User loggedPersonal) {
        var studentOptional = studentRepository.findByIdAndPersonalTrainer(id, loggedPersonal);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        studentRepository.delete(studentOptional.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent (@PathVariable UUID id, @RequestBody StudentUpdateRequest request, @AuthenticationPrincipal User loggedPersonal) {

        var studentOptional = studentRepository.findByIdAndPersonalTrainer(id, loggedPersonal);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var student = studentOptional.get();

        student.setName(request.name());
        student.setEmail(request.email());
        student.setPhone(request.phone());

        studentRepository.save(student);

        return ResponseEntity.ok(new StudentResponse(student));

    }
}
