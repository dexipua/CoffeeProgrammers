package com.school.dto.subject;

import com.school.models.Subject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransformSubject {

    public static Subject transformFromRequestToModel(SubjectRequest subjectRequest) {
        Subject model = new Subject();
        model.setName(subjectRequest.getName());
        return model;
    }
}
