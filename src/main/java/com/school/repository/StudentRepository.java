package com.school.repository;

import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(Long studentId);
    Optional<List<Subject>> findBySubjects(Long studentId);
    Optional<Student> findByEmail(String email);
    Optional<Student> findByUsername(String username);
}
