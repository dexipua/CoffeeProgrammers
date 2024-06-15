package com.school.service;

import com.school.models.SchoolNews;

import java.util.List;

public interface SchoolNewsService {
    SchoolNews create(SchoolNews news);
    SchoolNews findById(long id);
    void delete(long id);
    List<SchoolNews> getAllSchoolNews();
}
