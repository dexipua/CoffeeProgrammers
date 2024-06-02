package com.school.service;

import com.school.models.StudentNews;

import java.util.List;

public interface StudentNewsService {
    StudentNews create(StudentNews news);
    StudentNews findById(long id);
    List<StudentNews> getAllNewsByStudentId(long id);
}
