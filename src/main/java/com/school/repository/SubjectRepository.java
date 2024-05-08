package com.school.repository;

import com.school.models.Student;
import com.school.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(String name);
    Optional<List<Subject>> findAllByOrderByName();
    Optional<List<Subject>> findByTeacher_Id(long teacherId);
    @Query("SELECT s FROM Subject s JOIN s.students sub WHERE sub.user.username = :studentName")
    List<Student> findByStudentUsername(String studentName);
}
