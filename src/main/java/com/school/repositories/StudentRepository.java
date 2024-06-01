package com.school.repositories;

import com.school.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s.students FROM Subject s WHERE s.name = :subjectName")
    List<Student> findStudentBySubjectName(String subjectName);
    List<Student> findAllByOrderByUser();
    List<Student> findAllByUser_FirstNameAndUser_LastName(String firstName, String lastName);
}
