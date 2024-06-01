package com.school.service;

import com.school.models.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher create(Teacher teacher);
    Teacher findById(long id);
    Teacher update(Teacher teacher);
    void delete(long id);
    List<Teacher> findAll();
    List<Teacher> findBySubjectName(String subjectName);
    List<Teacher> findAllByUser_FirstNameAndAndUser_LastName(String firstName, String lastName);
}
