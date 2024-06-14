package com.school.repositories;

import com.school.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT s.teacher FROM Subject s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :subjectName, '%'))")
    List<Teacher> findBySubjectNameIgnoreCase(String subjectName);
    List<Teacher> findAllByOrderByUser_LastNameAsc();
    List<Teacher> findAllByUser_FirstNameContainingIgnoreCaseAndUser_LastNameContainingIgnoreCase(String firstName, String lastName);
    Optional<Teacher> findByUserId(Long userId);

    @Query("SELECT t FROM Teacher t JOIN t.subjects s JOIN s.students st WHERE st.id = :studentId")
    List<Teacher> findAllBySubjectsByStudents_Id(long studentId);}
