package com.school.repositories;

import com.school.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s.students FROM Subject s WHERE s.name = :subjectName")
    Optional<List<Student>> findStudentBySubjectsContains(String subjectName);
    Optional<Student> findByUserEmail(String email);
}
