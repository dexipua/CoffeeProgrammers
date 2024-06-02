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
    private int dayOfWeek;
    private int numOfLesson;
    public static SubjectDate toSubject(SubjectDateRequest subjectDateRequest, SubjectService subjectService, long subjectId){
        return new SubjectDate(
                subjectService.findById(subjectId),
                DayOfWeek.of(subjectDateRequest.dayOfWeek),
                subjectDateRequest.numOfLesson
        );
    }
}
