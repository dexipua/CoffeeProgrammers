package com.school.dto.mark;

import com.school.dto.student.StudentResponseSimple;
import com.school.models.Mark;
import com.school.models.Student;
import lombok.Data;

import java.util.List;

@Data
public class MarkResponseForSubject {
    private StudentResponseSimple studentResponseSimple;
    private MarkResponseSimple[] marks;

    public MarkResponseForSubject(Student student, List<Mark> marks) {
        this.studentResponseSimple = new StudentResponseSimple(student);

        int size = marks.size();
        this.marks = new MarkResponseSimple[size];
        for(int i = 0; i < size; i++) {
            this.marks[i] = new MarkResponseSimple(marks.get(i));
        }
    }
}
