package com.school.dto.student;

import com.school.dto.subject.SubjectResponseWithTeacher;
import com.school.models.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Data
public class StudentResponseAll {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private double averageMark;
    private List<SubjectResponseWithTeacher> subjects;

    public StudentResponseAll(Student student, double averageMark){
        this.id = student.getId();
        this.firstName = student.getUser().getFirstName();
        this.lastName = student.getUser().getLastName();
        this.email = student.getUser().getEmail();
        this.averageMark = Math.round(averageMark * 10.0) / 10.0;
        this.subjects = student.getSubjects().stream()
                .map(SubjectResponseWithTeacher::new)
                .collect(Collectors.toList());
    }
}