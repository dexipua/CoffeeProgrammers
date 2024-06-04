package com.school.service;

import com.school.dto.subject.SubjectRequest;
import com.school.models.Subject;

import java.util.List;

public interface SubjectService {
    Subject create(SubjectRequest subjectRequest);

    Subject findById(long id);

    Subject update(long subjectToUpdateId, SubjectRequest subjectRequest);

    void delete(long id);

    List<Subject> getAllByOrderByName();

    List<Subject> findByNameContaining(String name);

    List<Subject> findByTeacher_Id(long teacherId);

    void setTeacher(long subjectId, long teacherId);

    void deleteTeacher(long teacherId);

    List<Subject> findByStudent_Id(long studentId);

    void addStudent(long subjectId, long studentId);

    void deleteStudent(long subjectId, long studentId);
}
