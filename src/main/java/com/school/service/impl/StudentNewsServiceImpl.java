package com.school.service.impl;

import com.school.models.StudentNews;
import com.school.repositories.StudentNewsRepository;
import com.school.service.StudentNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentNewsServiceImpl implements StudentNewsService {
    private StudentNewsRepository newsRepository;

    @Override
    public StudentNews create(StudentNews news) {
        return newsRepository.save(news);
    }

    @Override
    public void delete(StudentNews news) {
        newsRepository.delete(news);
    }

    @Override
    public List<StudentNews> getAllNewsByStudentId(long studentId) {
        return newsRepository.findAllByStudent_Id(studentId);
    }
}
