package com.school.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;


@Entity
@Getter
@Setter
@Table(name = "subject_dates")
public class SubjectDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;
    @Column(name = "num_of_lesson")
    private NumOfLesson numOfLesson;
    @ManyToOne()
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public SubjectDate(Subject subject, DayOfWeek dayOfWeek, int numOfLesson) {
        this.subject = subject;
        this.dayOfWeek = dayOfWeek;
        this.numOfLesson = NumOfLesson.values()[numOfLesson - 1];
    }

    public SubjectDate() {
    }

    public enum NumOfLesson {
        FIRST(LocalTime.of(8, 0), LocalTime.of(8, 45)),
        SECOND(LocalTime.of(9, 0), LocalTime.of(9, 45)),
        THIRD(LocalTime.of(10, 0), LocalTime.of(10, 45)),
        FOURTH(LocalTime.of(11, 0), LocalTime.of(11, 45)),
        FIFTH(LocalTime.of(12, 0), LocalTime.of(12, 45)),
        SIXTH(LocalTime.of(13, 0), LocalTime.of(13, 45)),
        SEVENTH(LocalTime.of(14, 0), LocalTime.of(14, 45)),
        EIGHTH(LocalTime.of(15, 0), LocalTime.of(15, 45)),
        NINTH(LocalTime.of(16, 0), LocalTime.of(16, 45));

        final LocalTime start;
        final LocalTime end;

        NumOfLesson(LocalTime start, LocalTime end) {
            this.start = start;
            this.end = end;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectDate that)) return false;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }
}
