package com.treinodex.backend.api.student;

import com.treinodex.backend.domain.student.Student;
import com.treinodex.backend.domain.student.StudentRepository;
import com.treinodex.backend.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class  StudentControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController studentController;

    @Test
    void returnPageStudentsSucess() {

        User personalFalse = new User();
        personalFalse.setId(UUID.randomUUID());

        Pageable pageFalse = PageRequest.of(0, 10);

        Student studentFalse = new Student();
        Page<Student> studentPageFalse = new PageImpl<>(List.of(studentFalse));

        when(studentRepository.findAllByPersonalTrainer(personalFalse, pageFalse))
                .thenReturn(studentPageFalse);

        ResponseEntity<Page<StudentResponse>> response = studentController.listStudents(personalFalse, pageFalse);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    void returnPageEmptyStudents() {
        User personalNew = new  User();
        Pageable pageEmpty = PageRequest.of(0, 10);

        Page<Student> pageEmptyStudent = Page.empty();

        when(studentRepository.findAllByPersonalTrainer(personalNew, pageEmpty))
                .thenReturn(pageEmptyStudent);

        ResponseEntity<Page<StudentResponse>> response = studentController.listStudents(personalNew, pageEmpty);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(0, response.getBody().getTotalElements());
    }
}