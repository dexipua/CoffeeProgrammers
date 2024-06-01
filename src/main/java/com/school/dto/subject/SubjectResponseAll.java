package com.school.dto.subject;

import com.school.dto.student.StudentResponseToGet;
import com.school.dto.teacher.TeacherResponseToGet;
import com.school.models.Subject;
import lombok.Data;


@Data
public class SubjectResponseAll {
    long id;
    String name;
    TeacherResponseToGet teacher;
    StudentResponseToGet[] students;

    public SubjectResponseAll(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.teacher = subject.getTeacher() == null ? null : new TeacherResponseToGet(subject.getTeacher());
        try {
            this.students = new StudentResponseToGet[subject.getStudents().size()];
            int studentsSize = subject.getStudents().size();
            for (int i = 0; i < studentsSize; i++) {
                this.students[i] = new StudentResponseToGet(subject.getStudents().get(i));
            }
        }catch (NullPointerException e) {
            students = null;
        }
    }
}
