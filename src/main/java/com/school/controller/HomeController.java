package com.school.controller;

import com.school.dto.home.HomeResponse;
import com.school.dto.subject.SubjectResponseToGet;
import com.school.dto.teacher.TeacherResponseToGet;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import com.school.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final SubjectService subjectService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public HomeResponse homePage() {

        List<TeacherResponseToGet> teachers = teacherService.findAll().stream()
                .map(TeacherResponseToGet::new)
                .collect(Collectors.toList());

        List<SubjectResponseToGet> subjects = subjectService.getAllByOrderByName().stream()
                .map(SubjectResponseToGet::new)
                .collect(Collectors.toList());
        long amountOfStudents = studentService.findAllOrderedByName().size();

        return new HomeResponse(
                teachers,
                subjects,
                amountOfStudents);
    }
}
