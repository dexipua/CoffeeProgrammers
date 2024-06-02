package com.school.service;

import com.school.models.SchoolNews;

import java.util.List;

public interface SchoolNewsService {
    SchoolNews create(SchoolNews news);
    void delete(long id);
    SchoolNews findById(long id);
    List<SchoolNews> getAllSchoolNews();
}
