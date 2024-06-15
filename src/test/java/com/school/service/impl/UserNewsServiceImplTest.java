package com.school.service.impl;

import com.school.models.User;
import com.school.models.UserNews;
import com.school.repositories.UserNewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserNewsServiceImplTest {
    @Mock
    private UserNewsRepository userNewsRepository;

    @InjectMocks
    private UserNewsServiceImpl userNewsService;

    private UserNews userNews;

    private User user;
    @BeforeEach
    void setUp() {
        user = new User("Vlad", "Bulakovskyi", "email@gmail.com", "Vlad123");
        user.setId(1);
        userNews = new UserNews("New mark", user);
        userNews.setId(1);
    }

    @Test
    void create() {
        when(userNewsRepository.save(userNews)).thenReturn(userNews);
        userNewsService.create(userNews);
        verify(userNewsRepository, times(1)).save(userNews);
    }

    @Test
    void getAllNewsByUserId() {
        when(userNewsRepository.findAllByUser_IdOrderByTimeDesc(user.getId())).thenReturn(List.of(userNews));
        assertEquals(List.of(userNews), userNewsService.getAllNewsByUserId(user.getId()));
        verify(userNewsRepository, times(1)).findAllByUser_IdOrderByTimeDesc(user.getId());
    }
}