package com.school.service;

import com.school.models.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher create(Teacher teacher);
    Teacher findById(long id);
    Teacher update(Teacher teacher);
    void delete(long id);
    List<Teacher> findAllByOrderByUser();
    Teacher findBySubjectName(String subjectName);
    Teacher findByEmail(String email);
}
