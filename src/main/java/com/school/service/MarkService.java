package com.school.service;

import com.school.models.Mark;

import java.util.List;

public interface MarkService {
    Mark create(Mark mark);
    Mark findById(long id);
    Mark update(Mark mark);
    void delete(long id);
    List<Mark> findAllByStudentId(long studentId);
    List<Mark> findAllBySubjectId(long subjectId);
    List<Mark> findAllByFirstNameAndAndLastName(String firstName, String lastName);
    List<Mark> findAllBySubjectName(String subjectName);
    List<Mark> findAllByStudentIdAndSubjectId(long studentId, long subjectId);
    List<Mark> findAllByStudentNameAndSubjectName(String firstName, String lastName, String subjectName);
}
