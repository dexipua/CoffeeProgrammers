package com.school.dto.subject;

import com.school.models.Subject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SubjectRequest {

    @NotBlank(message = "Name must be provided")
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$",
            message = "Name can contain only letters, numbers and spaces")
    String name;

    public static Subject toSubject(SubjectRequest subjectRequest) {
        Subject subject = new Subject();
        subject.setName(subjectRequest.getName());
        return subject;
    }
}
