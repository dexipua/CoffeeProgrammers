package com.school.service;

import com.school.models.UserNews;

import java.util.List;

public interface UserNewsService {
    UserNews create(UserNews news);
    List<UserNews> getAllNewsByUserId(long id);
    void deleteAllByUserId(long userId);
}
