package com.school.dto.subject;

import com.school.dto.student.StudentResponseWithEmail;
import com.school.dto.teacher.TeacherResponseSimple;
import com.school.models.Subject;
import lombok.Data;


@Data
public class SubjectResponseAll {
    long id;
    String name;
    TeacherResponseSimple teacher;
    StudentResponseWithEmail[] students;

    public SubjectResponseAll(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        this.teacher = subject.getTeacher() == null ? null : new TeacherResponseSimple(subject.getTeacher());
        try {
            this.students = new StudentResponseWithEmail[subject.getStudents().size()];
            int studentsSize = subject.getStudents().size();
            for (int i = 0; i < studentsSize; i++) {
                this.students[i] = new StudentResponseWithEmail(subject.getStudents().get(i));
            }
        }catch (NullPointerException e) {
            students = null;
        }
    }
}
