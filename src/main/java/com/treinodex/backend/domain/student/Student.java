package com.treinodex.backend.domain.student;

import com.treinodex.backend.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "Student")
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_id")
    private User personalTrainer;

}
