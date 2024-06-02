package com.school.repositories;

import com.school.models.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    Optional<Mark> findById(long id);
    @Query("SELECT s.marks FROM Student s WHERE s.id = :studentId")
    List<Mark> findAllByStudentId(long studentId);
    @Query("SELECT s.marks FROM Subject s WHERE s.id = :subjectId")
    List<Mark> findAllBySubjectId(long subjectId);
}
