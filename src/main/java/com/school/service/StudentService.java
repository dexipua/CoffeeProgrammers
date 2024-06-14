package com.school.service;

import com.school.dto.user.UserRequestCreate;
import com.school.dto.user.UserRequestUpdate;
import com.school.models.Student;

import java.util.List;

public interface StudentService {
    Student create(UserRequestCreate userRequest);
    Student findById(long id);
    Student update(long studentToUpdateId, UserRequestUpdate userRequest);
    void deleteById(long id);
    List<Student> findAllOrderedByName();
    List<Student> findBySubjectName(String subName);
    List<Student> findStudentsByTeacherId(long teacherId);

    List<Student> findAllByUser_FirstNameAndUser_LastName(
            String firstName,
            String lastName);

    Student findByUserId(long userId);
    long getStudentsCount();
    List<Student> findAllBySubjectsIdIsNot(long subjectId);
}
