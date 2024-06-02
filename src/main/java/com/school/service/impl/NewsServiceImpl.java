package com.school.service.impl;

import com.school.models.News;
import com.school.repositories.NewsRepository;
import com.school.service.NewsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;

    @Override
    public News create(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News findById(long id) {
        return newsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<News> getAllNewsByStudentId(long studentId) {
        return newsRepository.findAllByStudent_Id(studentId);
    }
}
