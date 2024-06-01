package com.school.repositories;

import com.school.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(String name);
    List<Subject> findByNameContaining(String name);
    List<Subject> findAllByOrderByName();
    List<Subject> findByTeacher_Id(long teacherId);
    @Query("SELECT s.subjects FROM Student s WHERE s.id = :studentId")
    List<Subject> findByStudent_Id(long studentId);
}
