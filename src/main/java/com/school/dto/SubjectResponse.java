package com.school.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import lombok.Value;

import java.util.List;


@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubjectResponse {
    long id;
    String name;
    Teacher teacher; //TODO
    List<Student> students;

    public SubjectResponse(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.teacher = subject.getTeacher();
        this.students = subject.getStudents();
    }
}
