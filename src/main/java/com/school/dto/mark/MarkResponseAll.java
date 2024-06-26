package com.school.dto.mark;

import com.school.dto.student.StudentResponseSimple;
import com.school.dto.subject.SubjectResponseWithTeacher;
import com.school.models.Mark;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class MarkResponseAll {
    long id;
    private int value;
    private String time;
    private StudentResponseSimple studentResponseSimple;
    private SubjectResponseWithTeacher subjectResponseWithTeacher;

    public MarkResponseAll(Mark mark) {
        this.id = mark.getId();
        this.value = mark.getValue();
        this.time = mark.getTime().withNano(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.studentResponseSimple = new StudentResponseSimple(mark.getStudent());
        this.subjectResponseWithTeacher = new SubjectResponseWithTeacher(mark.getSubject());
    }
}