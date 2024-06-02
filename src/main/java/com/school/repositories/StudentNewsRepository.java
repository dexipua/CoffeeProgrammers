package com.school.repositories;

import com.school.models.StudentNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentNewsRepository extends JpaRepository<StudentNews, Long> {
    List<StudentNews> findAllByStudent_Id(Long id);
}
