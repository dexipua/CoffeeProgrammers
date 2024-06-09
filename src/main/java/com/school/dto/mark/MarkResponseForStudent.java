package com.school.dto.mark;

import com.school.dto.subject.SubjectResponseSimple;
import com.school.models.Mark;
import com.school.models.Subject;
import lombok.Data;

import java.util.List;

@Data
public class MarkResponseForStudent {
    private SubjectResponseSimple subjectResponseSimple;
    private MarkResponseSimple[] marks;

    public MarkResponseForStudent(Subject subject, List<Mark> marks) {
        this.subjectResponseSimple = new SubjectResponseSimple(subject);

        int size = marks.size();
        this.marks = new MarkResponseSimple[size];
        for(int i = 0; i < size; i++) {
            this.marks[i] = new MarkResponseSimple(marks.get(i));
        }
    }
}
