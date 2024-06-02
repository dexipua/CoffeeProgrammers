package com.school.dto.mark;

import com.school.dto.student.StudentResponseToGet;
import com.school.dto.subject.SubjectResponseToGet;
import com.school.models.Mark;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class MarkResponseAll {
    private int mark;
    private LocalDateTime time;
    private StudentResponseToGet studentResponseToGet;
    private SubjectResponseToGet subjectResponseToGet;

    public MarkResponseAll(Mark mark) {
        this.mark = mark.getMark();
        this.time = mark.getTime();
        this.studentResponseToGet = new StudentResponseToGet(mark.getStudent());
        this.subjectResponseToGet = new SubjectResponseToGet(mark.getSubject());
    }
}