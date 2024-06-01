package com.school.service;

import com.school.models.Student;

import java.util.List;

public interface StudentService {
    Student create(Student student);
    Student findById(long id);
    Student update(Student student);
    void deleteById(long id);
    List<Student> findAllOrderedByName();
    List<Student> findBySubjectName(String subName);
    List<Student> findStudentsByTeacherId(long teacherId);
    List<Student> findAllByUser_FirstNameAndAndUser_LastName(String firstName, String lastName);
}
