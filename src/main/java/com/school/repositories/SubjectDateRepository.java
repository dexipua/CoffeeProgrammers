package com.school.repositories;

import com.school.models.SubjectDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectDateRepository extends JpaRepository<SubjectDate, Long> {
    List<SubjectDate> findAllBySubject_Students_Id(@Param("studentId") long studentId);
}
