package com.school.repositories;


import com.school.models.UserNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNewsRepository extends JpaRepository<UserNews, Long> {
    List<UserNews> findAllByUser_IdOrderByTimeDesc(Long id);
}
