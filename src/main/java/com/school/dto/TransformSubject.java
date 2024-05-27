package com.school.dto;

import com.school.models.Subject;
import com.school.service.StudentService;
import com.school.service.TeacherService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@RequiredArgsConstructor
public class TransformSubject {

    public static Subject transformFromRequestToModel(SubjectRequest subjectRequest) {
        Subject model = new Subject();
        model.setName(subjectRequest.getName());
        return model;
    }
}
