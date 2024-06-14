package com.school.dto.mark;

import com.school.dto.subject.SubjectResponseSimple;
import com.school.models.Mark;
import com.school.models.Subject;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MarkResponseForStudent {
    private SubjectResponseSimple subjectResponseSimple;
    private List<MarkResponseSimple> marks;

    public MarkResponseForStudent(Subject subject, List<Mark> marks) {
        this.subjectResponseSimple = new SubjectResponseSimple(subject);

        this.marks = marks.stream()
                .map(MarkResponseSimple::new)
                .collect(Collectors.toList());
    }
}
