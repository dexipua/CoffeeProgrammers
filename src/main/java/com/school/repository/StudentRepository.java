package com.school.repository;

import com.school.models.Student;
import com.school.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStudentId(Long studentId);
    Student findByEmail(String email);
    Student findByUsername(String username);
}
