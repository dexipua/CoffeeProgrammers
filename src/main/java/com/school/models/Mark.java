package com.school.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.websocket.server.PathParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "marks")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "mark")
    @Min(value = 1, message = "Mark must be at least 1")
    @Max(value = 12, message = "Mark must be at most 12")
    private int mark;
    @Column(name = "timeOfMark")
    private LocalDateTime time = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Mark(int mark, Student student, Subject subject) {
        this.mark = mark;
        this.student = student;
        this.subject = subject;
    }
}
