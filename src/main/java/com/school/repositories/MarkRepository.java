package com.school.repositories;

import com.school.models.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    Optional<Mark> findById(long id);
    @Query("SELECT s.marks FROM Student s WHERE s.id = :studentId")
    List<Mark> findAllByStudentId(long studentId);
    List<Mark> findAllByStudent_User_FirstNameContainingAndStudent_User_LastNameContaining(String firstName, String lastName);
    List<Mark> findAllBySubjectId(long subjectId);
    List<Mark> findAllBySubject_Name(String name);
    List<Mark> findAllByStudentIdAndSubjectId(long studentId, long subjectId);
    List<Mark> findAllByStudent_User_FirstNameContainingAndStudent_User_LastNameContainingAndSubject_NameContaining(String firstName, String lastName, String name);
}
