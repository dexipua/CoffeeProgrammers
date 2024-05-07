package com.school.repository;

import com.school.models.Student;
import com.school.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(Long studentId);
    @Query("SELECT s FROM Student s JOIN s.subjects sub WHERE sub.name = :subjectName")
    List<Student> findBySubjectName(String subjectName);
    Optional<Student> findByEmail(String email);
    Optional<Student> findByUsername(String username);
}
