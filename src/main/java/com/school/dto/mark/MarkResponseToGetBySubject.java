package com.school.dto.mark;

import com.school.dto.student.StudentResponseToGet;
import com.school.models.Mark;
import com.school.models.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class MarkResponseToGetBySubject {
    private StudentResponseToGet studentResponseToGet;
    private MarkResponseToGet[] marks;

    public MarkResponseToGetBySubject(Student student, List<Mark> marks) {
        this.studentResponseToGet = new StudentResponseToGet(student);
        this.marks = new MarkResponseToGet[marks.size()];
        for(int i = 0; i < marks.size(); i++) {
            this.marks[i] = new MarkResponseToGet(marks.get(i));
        }
    }
}
