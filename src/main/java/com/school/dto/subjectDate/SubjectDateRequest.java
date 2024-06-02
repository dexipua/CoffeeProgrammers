package com.school.dto.subjectDate;

import com.school.models.SubjectDate;
import com.school.service.SubjectService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;

@Getter
@Setter
@Data
public class SubjectDateRequest {
    private long subject_id;
    private int dayOfWeek;
    private String numOfLesson;
    public static SubjectDate toSubject(SubjectDateRequest subjectDateRequest, SubjectService subjectService){
        return new SubjectDate(
                subjectService.findById(subjectDateRequest.getSubject_id()),
                DayOfWeek.of(subjectDateRequest.dayOfWeek),
                subjectDateRequest.numOfLesson
        );
    }
}
