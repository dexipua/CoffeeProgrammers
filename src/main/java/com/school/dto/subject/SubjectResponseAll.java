package com.school.dto.subject;

import com.school.dto.student.StudentResponseWithEmail;
import com.school.dto.teacher.TeacherResponseSimple;
import com.school.models.Subject;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


@Data
public class SubjectResponseAll {
    long id;
    String name;
    TeacherResponseSimple teacher;
    List<StudentResponseWithEmail> students;

    public SubjectResponseAll(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.teacher = subject.getTeacher() == null ? null : new TeacherResponseSimple(subject.getTeacher());
        try {
            this.students = subject.getStudents().stream()
                    .map(StudentResponseWithEmail::new)
                    .collect(Collectors.toList());
        }catch (NullPointerException e) {
            students = null;
        }
    }
}
