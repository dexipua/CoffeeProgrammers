package com.school.service;

import com.school.models.Mark;
import com.school.models.Student;
import com.school.models.Subject;

import java.util.HashMap;
import java.util.List;

public interface MarkService {
    Mark create(Mark mark);
    Mark findById(long id);
    Mark update(Mark mark);
    void delete(long id);
    HashMap<Subject, List<Mark>> findAllByStudentId(long studentId);
    HashMap<Student, List<Mark>> findAllBySubjectId(long subjectId);
}
