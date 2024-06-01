package com.school.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "marks")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "status")
    private String status;
    @Column(name = "date")
    private LocalDate date;
    @ManyToOne()
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne()
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
