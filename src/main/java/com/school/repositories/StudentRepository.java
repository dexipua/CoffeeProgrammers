package com.school.repositories;

import com.school.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s JOIN s.subjects sub WHERE sub.name = :subjectName")
    Optional<List<Student>> findBySubjectName(String subjectName);
    @Query("select s from Student s where s.user.email =: email")
    Optional<Student> findByEmail(String email);
    @Query("select s from Student s where s.user.username =: username")
    Optional<Student> findByUsername(String username);
}
