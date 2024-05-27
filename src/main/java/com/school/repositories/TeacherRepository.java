package com.school.repositories;

import com.school.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUserId(long id);
    Optional<Teacher> findByUserEmail(String email);
    @Query("SELECT s.teacher FROM Subject s WHERE s.name =:subject")
    Optional<Teacher> findBySubjectsContains(String subject);
}
