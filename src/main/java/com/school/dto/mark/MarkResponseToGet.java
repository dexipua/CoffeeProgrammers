package com.school.dto.mark;

import com.school.dto.student.StudentResponseToGet;
import com.school.dto.subject.SubjectResponseToGet;
import com.school.models.Mark;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class MarkResponseToGet {
    private int mark;
    private StudentResponseToGet studentResponseToGet;
    private SubjectResponseToGet subjectResponseToGet;

    public MarkResponseToGet(Mark mark) {
        this.mark = mark.getMark();
        this.studentResponseToGet = new StudentResponseToGet(mark.getStudent());
        this.subjectResponseToGet = new SubjectResponseToGet(mark.getSubject());
    }
}
