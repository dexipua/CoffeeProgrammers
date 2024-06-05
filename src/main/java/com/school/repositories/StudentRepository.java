package com.school.repositories;

import com.school.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s.students FROM Subject s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :subjectName, '%'))")
    List<Student> findStudentBySubjectNameContainingIgnoreCase(String subjectName);
    List<Student> findAllByOrderByUser();
    List<Student> findAllByUser_FirstNameContainingIgnoreCaseAndUser_LastNameContainingIgnoreCase(String firstName, String lastName);
    @Query("SELECT s.students FROM Subject s WHERE s.teacher.id = :teacherId")
    List<Student> findAllByTeacherId(long teacherId);
}
