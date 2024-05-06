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
@Setter
@Getter
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "subjectandstudents",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Student> subjects;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Override
    public String toString(){
        return "Student{" +
                "id=" + id +
                ", subjects=" + subjects +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student)o;
        return this.id == student.id && this.subjects.equals(student.subjects) && user.equals(student.user);
    }

    @Override
    public int hashCode(){
        int result = Long.hashCode(id);
        result = 17 * result + subjects.hashCode();
        result = 17 * result + user.hashCode();
        return result;
    }
}
