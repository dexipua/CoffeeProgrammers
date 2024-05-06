package com.school.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "teacher")
    private List<Subject> subjects;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;
        return user.getFirstName().equals(teacher.user.getFirstName())
                && user.getLastName().equals(teacher.user.getLastName())
                && id == teacher.id;
    }

    @Override
    public int hashCode() {
        return (int)(user.getFirstName().length() * 17 + user.getLastName().length() * 29 + id * 7);
    }

    @Override
    public String toString() {
        return "Teacher {" +
                    "firstName:" + user.getFirstName() +
                    ", lastName:" + user.getLastName() +
                    ", subjects:" + subjects +
                "}";
    }
}
