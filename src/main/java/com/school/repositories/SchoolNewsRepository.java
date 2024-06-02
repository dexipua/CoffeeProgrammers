package com.school.repositories;

import com.school.models.SchoolNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolNewsRepository extends JpaRepository<SchoolNews, Long> {}

