package com.school.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "teachers")
public class Teacher implements Comparable<Teacher> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "teacher")
    private List<Subject> subjects;

    public Teacher(User user) {
        this.user = user;
        this.subjects = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Teacher {" +
                "firstName:" + user.getFirstName() +
                ", lastName:" + user.getLastName() +
                ", subjects:" + subjects +
                "}";
    }

    @Override
    public int compareTo(Teacher o) {
        return this.user.compareTo(o.user);
    }
}
