package com.school.repositories;

import com.school.models.SchoolNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolNewsRepository extends JpaRepository<SchoolNews, Long> {
    List<SchoolNews> findAllByOrderByTimeDesc();
}

