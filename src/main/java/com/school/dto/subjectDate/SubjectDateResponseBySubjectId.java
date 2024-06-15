package com.school.dto.subjectDate;

import com.school.models.SubjectDate;
import lombok.Data;

import java.time.DayOfWeek;

@Data
public class SubjectDateResponseBySubjectId {
    private Long id;
    private String dayOfWeek;
    private String numOfLesson;
    public SubjectDateResponseBySubjectId(DayOfWeek dayOfWeek, SubjectDate.NumOfLesson numOfLesson, Long id) {
        this.id = id;
        this.dayOfWeek = dayOfWeek.toString();
        this.numOfLesson = numOfLesson.toString();

    }
}
