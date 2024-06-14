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
@Table(name = "news")
public class UserNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "time")
    private LocalDateTime time;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    public UserNews(String title, User user) {
        this.title = title;
        this.user = user;
        this.time = LocalDateTime.now();
    }
}
