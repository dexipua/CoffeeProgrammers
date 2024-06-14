package com.school.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "schoolnews")
public class SchoolNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String text;
    @Column(name = "time")
    private LocalDateTime time;
    public SchoolNews(String text, LocalDateTime time) {
        this.text = text;
        this.time = time;
    }

    public SchoolNews(String text) {
        this.text = text;
        this.time = LocalDateTime.now();
    }
}