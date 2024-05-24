package com.school.dto;

import com.school.models.Subject;
import lombok.Data;


@Data
public class SubjectResponse {
    long id;
    String name;
    Long teacherId; //TODO -Long or long-
    Long[] studentsId;

    public SubjectResponse(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.teacherId = subject.getTeacher() == null ? null : subject.getTeacher().getId();
        this.studentsId = new Long[subject.getStudents().size()];
        int studentsSize = subject.getStudents().size();
        for (int i = 0; i < studentsSize; i++) {
            this.studentsId[i] = subject.getStudents().get(i).getId();
        }

    }
}
