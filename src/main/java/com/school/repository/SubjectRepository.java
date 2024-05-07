package com.school.repository;

import com.school.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByName(String name);
    List<Subject> findAllByOrderByName();
    List<Subject> findByTeacher_Id(long teacherId);

}
