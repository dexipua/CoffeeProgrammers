package com.school.models;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "teacher_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "5"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.DETACH)
    private List<Subject> subjects;

    @Override
    public String toString() {
        return "Teacher {" +
                    "firstName:" + user.getFirstName() +
                    ", lastName:" + user.getLastName() +
                    ", subjects:" + subjects +
                "}";
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public Teacher(User user) {
        this.user = user;
        this.subjects = new ArrayList<>();
    }
}
