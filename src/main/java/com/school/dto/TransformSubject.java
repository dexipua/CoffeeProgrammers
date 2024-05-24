package com.school.dto;

import com.school.models.Subject;
import com.school.service.StudentService;
import com.school.service.TeacherService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@RequiredArgsConstructor
public class TransformSubject {

    public static Subject transformFromRequestToModel(TeacherService teacherService,
                                                      StudentService studentService,
                                                      SubjectRequest subjectRequest) {
        Subject model = new Subject();
        model.setName(subjectRequest.getName());

        model.setTeacher(subjectRequest.teacherId == null ?
                null : teacherService.readById(subjectRequest.teacherId));

        Long[] studentsId = subjectRequest.getStudentsId();

        model.setStudents(new ArrayList<>());
        Arrays.stream(studentsId).
                forEach(studentId -> model.getStudents().
                        add(studentService.findById(studentId)));
        return model;
    }
}
