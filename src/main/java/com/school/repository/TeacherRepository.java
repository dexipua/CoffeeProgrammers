package com.school.repository;

import com.school.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findById(long id);
    @Query("select t from Teacher t where t.user.id =: id")
    Optional<Teacher> findByUserId(long id);
    @Query("select t from Teacher t where t.user.email =: email")
    Optional<Teacher> findByEmail(String email);
    @Query("select t from Teacher t where t.user.username =: username")
    Optional<Teacher> findByUsername(String username);
}
