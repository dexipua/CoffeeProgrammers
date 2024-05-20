package com.school.service;

import com.school.models.Subject;

import java.util.List;

public interface SubjectService {
    Subject create(Subject subject);
    Subject readById(long id);
    Subject update(Subject subject);
    void delete(long id);
    List<Subject> getAllByOrderByName();
    Subject findByName(String name);
    List<Subject> findByTeacher_Id(long teacherId);
    List<Subject> findByStudent_Id(long studentId);
}
