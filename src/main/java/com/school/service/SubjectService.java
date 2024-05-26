package com.school.service;

import com.school.models.Subject;

import java.util.List;

public interface SubjectService {
    Subject create(Subject subject);

    Subject findById(long id);

    Subject update(Subject subject);

    void delete(long id);

    List<Subject> getAllByOrderByName();

    Subject findByName(String name);

    List<Subject> findByTeacher_Id(long teacherId);

    void setTeacher(long subjectId, long teacherId);

    void deleteTeacher(long teacherId);

    List<Subject> findByStudent_Id(long studentId);

    void addStudent(long subjectId, long studentId);

    void deleteStudent(long subjectId, long studentId);
}
