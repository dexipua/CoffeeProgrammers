package com.school.dto.subject;

import com.school.dto.teacher.TeacherResponseSimple;
import com.school.models.Subject;
import lombok.Data;

@Data
public class SubjectResponseWithTeacher {
    long id;
    String name;
    TeacherResponseSimple teacher;

    public SubjectResponseWithTeacher(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.teacher = subject.getTeacher() == null ? null : new TeacherResponseSimple(subject.getTeacher());
    }
}
