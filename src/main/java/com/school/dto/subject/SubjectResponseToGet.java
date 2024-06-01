package com.school.dto.subject;

import com.school.dto.teacher.TeacherResponseToGet;
import com.school.models.Subject;
import lombok.Data;

@Data
public class SubjectResponseToGet {
    long id;
    String name;
    TeacherResponseToGet teacher;

    public SubjectResponseToGet(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.teacher = subject.getTeacher() == null ? null : new TeacherResponseToGet(subject.getTeacher());
    }
}