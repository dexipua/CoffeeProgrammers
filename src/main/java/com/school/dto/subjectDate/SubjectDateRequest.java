package com.school.dto.subjectDate;

import com.school.models.SubjectDate;
import com.school.service.SubjectService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;

@Getter
@Setter
@Data
public class SubjectDateRequest {
    @Min(value = 1, message = "Day of week must be greater than 0")
    @Max(value = 7, message = "Day of week must be less than 8")
    private int dayOfWeek;

    @Min(value = 1, message = "Number of lesson must be greater than 0")
    @Max(value = 9, message = "Number of lesson must be less than 10")
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