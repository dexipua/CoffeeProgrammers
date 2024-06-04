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
@Setter
@Getter
@Table(name = "students")
@EqualsAndHashCode
public class Student implements Comparable<Student> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @JoinTable(name = "subject_students",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjects;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Student(User user){
        this.user = user;
        this.subjects = new ArrayList<>();

    }
    @Override
    public String toString(){
        return "Student{" +
                "id=" + id +
                ", subjects=" + subjects +
                ", user=" + user +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        return this.user.compareTo(o.user);
    }
}
