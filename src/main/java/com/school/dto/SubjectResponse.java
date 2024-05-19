package com.school.dto;

import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class SubjectResponse {
    private long id;
    private String name;
    private Teacher teacher;
    private List<Student> students;

    public SubjectResponse(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.teacher = subject.getTeacher();
        this.students = subject.getStudents();
    }
}
