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
    private String title;
    @Column(name = "time")
    private LocalDateTime time;
    public SchoolNews(String title, LocalDateTime time) {
        this.title = title;
        this.time = time;
    }

    public SchoolNews(String title) {
        this.title = title;
        this.time = LocalDateTime.now();
    }
}