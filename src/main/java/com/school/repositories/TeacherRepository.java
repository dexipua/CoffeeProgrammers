package com.school.repositories;

import com.school.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT s.teacher FROM Subject s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :subjectName, '%'))")
    List<Teacher> findBySubjectNameIgnoreCase(String subjectName);
    List<Teacher> findAllByOrderByUser_LastNameAsc();
    List<Teacher> findAllByUser_FirstNameContainingIgnoreCaseAndUser_LastNameContainingIgnoreCase(String firstName, String lastName);
}
