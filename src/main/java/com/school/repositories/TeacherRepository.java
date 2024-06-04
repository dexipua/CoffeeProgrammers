package com.school.repositories;

import com.school.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT s.teacher FROM Subject s WHERE s.name LIKE %:subjectName%")
    List<Teacher> findBySubjectName(String subjectName);
    List<Teacher> findAllByOrderByUser();
    List<Teacher> findAllByUser_FirstNameContainingAndUser_LastNameContaining(String firstName, String lastName);
}
