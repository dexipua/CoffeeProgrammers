package com.school.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "^[A-Za-z0-9\\s]+$",
            message = "Can contain only letters, numbers and spaces")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "teacher")
    private Teacher teacher;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="subjectandstudents",
            joinColumns=  @JoinColumn(name="subject_id", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="student_id", referencedColumnName="id") )
    private List<Student> students;


    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", students=" + students +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject subject)) return false;

        return id == subject.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
