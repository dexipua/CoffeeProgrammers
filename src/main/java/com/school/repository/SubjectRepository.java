package com.school.repository;

import com.school.models.Role;
import com.school.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findById(Long id);
    Optional<Subject> findByName(String name);
    Optional<List<Subject>> findAllByOrderByName();
    Optional<List<Subject>> findByTeacher_Id(long teacherId);
}
