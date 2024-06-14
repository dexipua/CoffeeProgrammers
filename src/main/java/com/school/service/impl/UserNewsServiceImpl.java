package com.school.service.impl;

import com.school.models.UserNews;
import com.school.repositories.UserNewsRepository;
import com.school.service.UserNewsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserNewsServiceImpl implements UserNewsService {
    private final UserNewsRepository newsRepository;

    @Override
    public UserNews create(UserNews news) {
        return newsRepository.save(news);
    }

    @Override
    public UserNews findById(long id) {
        return newsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("UserNews with id " + id + " not found"));
    }

    @Override
    public List<UserNews> getAllNewsByUserId(long userId) {
        return newsRepository.findAllByUser_IdOrderByTimeDesc(userId);
    }

    @Transactional
    @Override
    public void deleteAllByUserId(long userId) {
        newsRepository.deleteAllByUser_Id(userId);
    }
}
