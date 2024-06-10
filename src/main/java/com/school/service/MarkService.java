package com.school.service;

import com.school.dto.mark.MarkRequest;
import com.school.models.Mark;
import com.school.models.Student;
import com.school.models.Subject;

import java.util.HashMap;
import java.util.List;

public interface MarkService {
    Mark create(MarkRequest markRequest, long subjectId, long studentId);
    Mark findById(long id);
    Mark update(long markToUpdateId, MarkRequest markRequest);
    void delete(long id);
    HashMap<Subject, List<Mark>> findAllByStudentId(long studentId);
    HashMap<Student, List<Mark>> findAllBySubjectId(long subjectId);
    double findAverageByStudentId(long studentId);
}
