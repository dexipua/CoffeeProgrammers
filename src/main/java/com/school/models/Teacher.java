package com.school.models;

import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "teacher")
    private List<Subject> subjects;

    @Override
    public String toString() {
        return "Teacher {" +
                    "firstName:" + user.getFirstName() +
                    ", lastName:" + user.getLastName() +
                    ", subjects:" + subjects +
                "}";
    }

    public Teacher(User user) {
        this.user = user;
        this.subjects = new ArrayList<>();
    }
}
