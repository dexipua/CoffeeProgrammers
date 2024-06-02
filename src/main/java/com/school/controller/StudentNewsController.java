package com.school.controller;

import com.school.dto.StudentNewsResponse;
import com.school.models.StudentNews;
import com.school.service.StudentNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student_news")
public class StudentNewsController {
    private final StudentNewsService newsService;

    @GetMapping("/{student_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<StudentNewsResponse> getAllNewsByStudentId(@PathVariable("student_id") int studentId) {
        List<StudentNews> news = newsService.getAllNewsByStudentId(studentId);
        return news.stream().map(StudentNewsResponse::new).toList();
    }
}
