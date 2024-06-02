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
        if(subjectDateRequest.numOfLesson < 0 || subjectDateRequest.numOfLesson > 9){
            throw new IllegalArgumentException("numOfLesson of lesson must be between 0 and 9");
        }
        return new SubjectDate(
                subjectService.findById(subjectId),
                DayOfWeek.of(subjectDateRequest.dayOfWeek),
                subjectDateRequest.numOfLesson
        );
    }
}
