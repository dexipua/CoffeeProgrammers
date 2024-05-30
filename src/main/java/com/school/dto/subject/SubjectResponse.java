package com.school.dto.subject;

import com.school.dto.student.StudentResponseToGet;
import com.school.dto.teacher.TeacherResponseToGet;
import com.school.models.Subject;
import lombok.Data;


@Data
public class SubjectResponse {
    long id;
    String name;
    TeacherResponseToGet teacherId;
    StudentResponseToGet[] studentsId;

    public SubjectResponse(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.teacherId = subject.getTeacher() == null ? null : new TeacherResponseToGet(subject.getTeacher());
        try {
            this.studentsId = new StudentResponseToGet[subject.getStudents().size()];
            int studentsSize = subject.getStudents().size();
            for (int i = 0; i < studentsSize; i++) {
                this.studentsId[i] = new StudentResponseToGet(subject.getStudents().get(i));
            }
        }catch (NullPointerException e) {
            studentsId = null;
        }
    }
}
