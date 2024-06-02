package com.school.service.impl;

import com.school.models.StudentNews;
import com.school.repositories.StudentNewsRepository;;
import com.school.service.StudentNewsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentNewsServiceImpl implements StudentNewsService {
    private final StudentNewsRepository studentNewsRepository;

    @Override
    public StudentNews create(StudentNews news) {
        return studentNewsRepository.save(news);
    }

    @Override
    public StudentNews findById(long id) {
        return studentNewsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<StudentNews> getAllNewsByStudentId(long studentId) {
        return studentNewsRepository.findAllByStudent_Id(studentId);
    }
}
