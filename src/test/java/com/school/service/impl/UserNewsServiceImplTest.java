package com.school.service.impl;

import com.school.models.User;
import com.school.models.UserNews;
import com.school.repositories.UserNewsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
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
    void findById() {
        when(userNewsRepository.findById(userNews.getId())).thenReturn(Optional.of(userNews));
        assertEquals(userNews, userNewsService.findById(userNews.getId()));
        verify(userNewsRepository, times(1)).findById(userNews.getId());
    }

    @Test
    void notFindById() {
        assertThrowsExactly(EntityNotFoundException.class,() -> userNewsService.findById(-1));
        verify(userNewsRepository, times(1)).findById((long) -1);
    }

    @Test
    void getAllNewsByUserId() {
        when(userNewsRepository.findAllByUser_Id(user.getId())).thenReturn(List.of(userNews));
        assertEquals(List.of(userNews), userNewsService.getAllNewsByUserId(user.getId()));
        verify(userNewsRepository, times(1)).findAllByUser_Id(user.getId());
    }
}