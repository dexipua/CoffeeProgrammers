package com.school.service;

import com.school.models.StudentNews;

import java.util.List;

public interface StudentNewsService {
    StudentNews create(StudentNews news);
    void delete(StudentNews news);
    List<StudentNews> getAllNewsByStudentId(long id);
}
