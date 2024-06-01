package com.school.repositories;

import com.school.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s.students FROM Subject s WHERE s.name LIKE %:subjectName%")
    List<Student> findStudentBySubjectNameContaining(String subjectName);
    List<Student> findAllByOrderByUser();
    List<Student> findAllByUser_FirstNameContainingAndUser_LastNameContaining(String firstName, String lastName);
    @Query("SELECT s.students FROM Subject s WHERE s.teacher.id = :teacherId")
    List<Student> findAllByTeacherId(long teacherId);
}
