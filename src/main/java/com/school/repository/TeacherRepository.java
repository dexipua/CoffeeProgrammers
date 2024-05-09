package com.school.repository;

import com.school.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher t WHERE t.user.id =:id")
    Optional<Teacher> findByUserId(long id);
    @Query("SELECT t FROM Teacher t WHERE t.user.email =:email")
    Optional<Teacher> findByEmail(String email);
    @Query("SELECT t FROM Teacher t WHERE t.user.username =:username")
    Optional<Teacher> findByUsername(String username);
}
