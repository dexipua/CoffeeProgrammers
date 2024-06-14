package com.school.dto.subjectDate;

import com.school.models.SubjectDate;
import lombok.Data;

import java.time.DayOfWeek;

@Data
public class SubjectDateResponseBySubjectId {
    private String dayOfWeek;
    private String numOfLesson;
    private boolean isPresent;
    public SubjectDateResponseBySubjectId(DayOfWeek dayOfWeek, SubjectDate.NumOfLesson numOfLesson, boolean isPresent) {
        this.dayOfWeek = dayOfWeek.toString();
        this.numOfLesson = numOfLesson.toString();
        this.isPresent = isPresent;
    }
}
