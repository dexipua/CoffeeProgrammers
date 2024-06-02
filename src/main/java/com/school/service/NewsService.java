package com.school.service;

import com.school.models.News;

import java.util.List;

public interface NewsService {
    News create(News news);
    void delete(News news);
    List<News> getAllNewsByStudentId(long id);
}
