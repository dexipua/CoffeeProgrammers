package com.school.service.impl;

import com.school.models.News;
import com.school.repositories.NewsRepository;
import com.school.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Override
    public News create(News news) {
        return newsRepository.save(news);
    }

    @Override
    public void delete(News news) {
        newsRepository.delete(news);
    }

    @Override
    public List<News> getAllNewsByStudentId(long studentId) {
        return newsRepository.findAllByStudent_Id(studentId);
    }
}
