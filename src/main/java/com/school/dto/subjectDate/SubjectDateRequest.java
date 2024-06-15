package com.school.dto.subjectDate;

import com.school.models.SubjectDate;
import com.school.service.SubjectService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.DayOfWeek;

@Data
public class SubjectDateRequest {
    @Min(value = 1, message = "Day of week must be greater than 0")
    @Max(value = 7, message = "Day of week must be less than 8")
    private int dayOfWeek;

    @Min(value = 1, message = "Number of lesson must be greater than 0")
    @Max(value = 8, message = "Number of lesson must be less than 9")
    private int numOfLesson;

    public static SubjectDate toSubject(
            SubjectDateRequest subjectDateRequest,
            SubjectService subjectService,
            long subjectId) {

        return new SubjectDate(
                subjectService.findById(subjectId),
                DayOfWeek.of(subjectDateRequest.dayOfWeek),
                subjectDateRequest.numOfLesson
        );
    }
}
