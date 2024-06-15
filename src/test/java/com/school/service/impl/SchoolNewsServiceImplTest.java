package com.school.service.impl;

import com.school.models.SchoolNews;
import com.school.repositories.SchoolNewsRepository;
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
class SchoolNewsServiceImplTest {
    @Mock
    private SchoolNewsRepository schoolNewsRepository;

    @InjectMocks
    private SchoolNewsServiceImpl schoolNewsService;

    private SchoolNews schoolNews;
    @BeforeEach
    void setUp() {
        schoolNews = new SchoolNews("You needn`t go to school tomorrow");
        schoolNews.setId(1);
    }
    @Test
    void create() {
        when(schoolNewsRepository.save(schoolNews)).thenReturn(schoolNews);
        schoolNewsService.create(schoolNews);
        verify(schoolNewsRepository, times(1)).save(schoolNews);
    }

    @Test
    void delete() {
        when(schoolNewsRepository.findById(schoolNews.getId())).thenReturn(Optional.of(schoolNews));
        schoolNewsService.delete(schoolNews.getId());
        verify(schoolNewsRepository, times(1)).findById(schoolNews.getId());
        verify(schoolNewsRepository, times(1)).delete(schoolNews);
    }

    @Test
    void findById() {
        when(schoolNewsRepository.findById(schoolNews.getId())).thenReturn(Optional.of(schoolNews));
        assertEquals(schoolNews, schoolNewsService.findById(schoolNews.getId()));
        verify(schoolNewsRepository, times(1)).findById(schoolNews.getId());
    }

    @Test
    void notFindById() {
        assertThrowsExactly(EntityNotFoundException.class,() -> schoolNewsService.findById(-1));
        verify(schoolNewsRepository, times(1)).findById((long) -1);
    }

    @Test
    void getAllSchoolNews() {
        SchoolNews schoolNews1 = new SchoolNews("The fair will be held tomorrow at 11 o'clock");
        when(schoolNewsRepository.findAllByOrderByTimeDesc()).thenReturn(List.of(schoolNews1, schoolNews));
        assertEquals(List.of(schoolNews1, schoolNews), schoolNewsService.getAllSchoolNews());
        verify(schoolNewsRepository, times(1)).findAllByOrderByTimeDesc();
    }

    @Test
    void update() {
        when(schoolNewsRepository.findById(schoolNews.getId())).thenReturn(Optional.of(schoolNews));
        SchoolNews schoolNews2 = new SchoolNews("Some text");
        schoolNews2.setId(1);

        schoolNewsService.update(schoolNews2);

        verify(schoolNewsRepository, times(1)).save(any(SchoolNews.class));
        verify(schoolNewsRepository, times(1)).findById(schoolNews.getId());
    }
}