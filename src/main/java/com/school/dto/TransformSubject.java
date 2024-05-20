package com.school.dto;

import com.school.models.Subject;

public class TransformSubject {

    public static Subject transformFromRequestToModel(SubjectRequest subjectRequest){
        Subject result = new Subject();
        result.setName(subjectRequest.getName());
        return result;
    }
}
