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

    private final SchoolNewsRepository schoolNewsRepository;

    @Override
    public SchoolNews create(SchoolNews news) {
        return schoolNewsRepository.save(news);
    }

    @Override
    public SchoolNews update(SchoolNews news) {
        SchoolNews schoolNews = findById(news.getId());
        schoolNews.setText(news.getText());
        schoolNews.setTime(news.getTime());
        return schoolNewsRepository.save(schoolNews);
    }

    @Override
    public void delete(long id) {
        SchoolNews schoolNews = findById(id);
        schoolNewsRepository.delete(schoolNews);
    }

    @Override
    public SchoolNews findById(long id) {
        return schoolNewsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("SchoolNews with id " + id + " not found"));
    }

    @Override
    public List<SchoolNews> getAllSchoolNews() {
        return schoolNewsRepository.findAllByOrderByTimeDesc();
    }
}
