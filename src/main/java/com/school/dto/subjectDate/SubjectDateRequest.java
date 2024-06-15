package com.school.dto.subjectDate;

import com.school.models.SubjectDate;
import com.school.service.SubjectService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;

@Data
public class SubjectDateRequest {

    @NotNull(message = "Day of week must be provided")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Num of lesson must be provided")
    private SubjectDate.NumOfLesson numOfLesson;

    public static SubjectDate toSubject(
            SubjectDateRequest subjectDateRequest,
            SubjectService subjectService,
            long subjectId) {

        return new SubjectDate(
                subjectService.findById(subjectId),
                subjectDateRequest.dayOfWeek,
                subjectDateRequest.numOfLesson
        );
    }
}
