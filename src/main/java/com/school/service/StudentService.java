package com.school.service;

import com.school.models.Student;
import java.util.List;

public interface StudentService {
    Student create(Student student);
    Student findById(long id);
    Student update(Student student);
    void deleteById(long id);
    List<Student> findAll();
    List<Student> findBySubjectName(String subName);
    Student findByEmail(String email);

}
