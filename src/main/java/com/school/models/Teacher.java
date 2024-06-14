package com.school.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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
    private Set<Subject> subjects = new HashSet<>();

    public Teacher(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "TeacherProfile {" +
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
