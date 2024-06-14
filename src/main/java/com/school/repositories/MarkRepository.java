package com.school.repositories;

import com.school.models.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findAllByStudent_Id(long studentId);
    List<Mark> findAllBySubject_Id(long subjectId);
}
