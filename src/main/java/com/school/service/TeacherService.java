package com.school.service;

import com.school.dto.user.UserRequestCreate;
import com.school.dto.user.UserRequestUpdate;
import com.school.models.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher create(UserRequestCreate userRequest);
    Teacher findById(long id);
    Teacher update(long teacherToUpdateId, UserRequestUpdate userRequest);
    void delete(long id);
    List<Teacher> findAll();
    List<Teacher> findBySubjectName(String subjectName);
    List<Teacher> findAllByUser_FirstNameAndAndUser_LastName(String firstName, String lastName);
    Teacher findByUserId(long userId);
}
