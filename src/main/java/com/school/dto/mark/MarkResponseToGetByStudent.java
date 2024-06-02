package com.school.dto.mark;

import com.school.dto.subject.SubjectResponseToGet;
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
    private SubjectResponseToGet subjectResponseToGet;
    private MarkResponseToGet[] marks;

    public MarkResponseToGetByStudent(Subject subject, List<Mark> marks) {
        this.subjectResponseToGet = new SubjectResponseToGet(subject);
        this.marks = new MarkResponseToGet[marks.size()];
        for(int i = 0; i < marks.size(); i++) {
            this.marks[i] = new MarkResponseToGet(marks.get(i));
        }
    }
}
