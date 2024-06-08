package com.school.controller;

import com.school.dto.schoolNews.SchoolNewsRequest;
import com.school.dto.schoolNews.SchoolNewsResponse;
import com.school.models.SchoolNews;
import com.school.service.impl.SchoolNewsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schoolNews")
public class SchoolNewsController {

    private final SchoolNewsServiceImpl schoolNewsService;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<SchoolNewsResponse> getAll() {
        List<SchoolNewsResponse> result = new ArrayList<>();
        for(SchoolNews schoolNews : schoolNewsService.getAllSchoolNews()) {
            result.add(new SchoolNewsResponse(schoolNews));
        }
        return result;
    }

    @PostMapping("/createSchoolNews")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    public SchoolNewsResponse create(@RequestBody SchoolNewsRequest schoolNewsRequest) {
        SchoolNews schoolNews = SchoolNewsRequest.toSchoolNews(schoolNewsRequest);
        return new SchoolNewsResponse(schoolNewsService.create(schoolNews));
    }

    @DeleteMapping("/delete/{school_news_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    public void deleteSchoolNews(@PathVariable long school_news_id) {
        schoolNewsService.delete(school_news_id);
    }

    @GetMapping("/getById/{school_news_id}")
    @ResponseStatus(HttpStatus.OK)
    public SchoolNewsResponse getById(@PathVariable long school_news_id) {
        return new SchoolNewsResponse(schoolNewsService.findById(school_news_id));
    }
}
