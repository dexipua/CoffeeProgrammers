package com.school.service.impl;

import com.school.models.SchoolNews;
import com.school.repositories.SchoolNewsRepository;
import com.school.service.SchoolNewsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class SchoolNewsServiceImpl implements SchoolNewsService {

    private SchoolNewsRepository schoolNewsRepository;
    private final SchoolNewsRepository schoolNewsRepository;

    @Override
    public SchoolNews create(SchoolNews news) {
        return schoolNewsRepository.save(news);
    }

    @Override
    public void delete(long id) {
        SchoolNews schoolNews = findById(id);
        schoolNewsRepository.delete(schoolNews);
    }

    @Override
    public SchoolNews findById(long id) {
        return schoolNewsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<SchoolNews> getAllSchoolNews() {
        return schoolNewsRepository.findAll();
    }
}
