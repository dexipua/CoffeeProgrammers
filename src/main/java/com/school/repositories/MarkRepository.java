package com.school.repositories;

import com.school.models.Mark;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    Optional<Mark> findById(long id);
    //@Query("SELECT m FROM Mark m WHERE m.student.id = :studentId")
    List<Mark> findAllByStudent_Id(long studentId);
    //@Query("SELECT m FROM Mark m WHERE m.subject.id = :subjectId")
    List<Mark> findAllBySubject_Id(long subjectId);
}
