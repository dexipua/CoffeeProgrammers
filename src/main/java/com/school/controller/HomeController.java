package com.school.controller;

import com.school.dto.HomeResponse;
import com.school.dto.SubjectResponse;
import com.school.dto.TeacherResponseToGet;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import com.school.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final SubjectService subjectService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public HomeResponse homePage(){
        return new HomeResponse(teacherService, subjectService, studentService);
    }
}