package com.school.dto.subjectDate;

import com.school.dto.subject.SubjectResponseToGet;
import com.school.models.Subject;
import com.school.models.SubjectDate;
import lombok.Data;

import java.time.DayOfWeek;

@Data
public class SubjectDateResponseByStudentId {
    private String dayOfWeek;
    private String numOfLesson;
    private SubjectResponseToGet subjectResponseToGet;
    public SubjectDateResponseByStudentId(DayOfWeek dayOfWeek, SubjectDate.NumOfLesson numOfLesson, Subject subject) {
        this.dayOfWeek = dayOfWeek.toString();
        this.numOfLesson = numOfLesson.toString();
        this.subjectResponseToGet = new SubjectResponseToGet(subject);
    }
}
