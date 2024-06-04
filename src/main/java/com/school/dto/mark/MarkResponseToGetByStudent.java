package com.school.dto.mark;

import com.school.dto.subject.SubjectResponseSimple;
import com.school.models.Mark;
import com.school.models.Subject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class MarkResponseToGetByStudent {
    private SubjectResponseSimple subjectResponseSimple;
    private MarkResponseSimple[] marks;

    public MarkResponseToGetByStudent(Subject subject, List<Mark> marks) {
        this.subjectResponseSimple = new SubjectResponseSimple(subject);

        int size = marks.size();
        this.marks = new MarkResponseSimple[size];
        for(int i = 0; i < size; i++) {
            this.marks[i] = new MarkResponseSimple(marks.get(i));
        }
    }
}
