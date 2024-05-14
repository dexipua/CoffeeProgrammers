package com.school.service;

import com.school.models.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher create(Teacher teacher);
    Teacher readById(long id);
    Teacher update(Teacher teacher);
    void delete(long id);
    List<Teacher> findAll();
    Teacher findBySubjectName(String subjectName);
    Teacher findByUsername(String username);
    Teacher findByEmail(String email);
}
