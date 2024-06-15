package com.school.dto.subjectDate;

import com.school.models.SubjectDate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import com.school.models.Subject;
import java.time.DayOfWeek;

@Data
public class SubjectDateRequest {

    @NotNull(message = "Day of week must be provided")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Num of lesson must be provided")
    private SubjectDate.NumOfLesson numOfLesson;

    public static SubjectDate toSubject(
            SubjectDateRequest subjectDateRequest,
            Subject subject) {

        return new SubjectDate(
                subject,
                subjectDateRequest.dayOfWeek,
                subjectDateRequest.numOfLesson
        );
    }
}
