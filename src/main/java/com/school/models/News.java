package com.school.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "time")
    private LocalDateTime time;
    @ManyToOne()
    @JoinColumn(name = "subject_id")
    private Student student;
    public News(String title, Student student) {
        this.title = title;
        this.student = student;
        this.time = LocalDateTime.now();
    }
}
