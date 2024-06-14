package com.school.controller;

import com.school.dto.home.HomeResponse;
import com.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final StudentService studentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public HomeResponse homePage() {

//        List<TeacherResponseSimple> teachers = teacherService.findAll().stream()
//                .map(TeacherResponseSimple::new)
//                .collect(Collectors.toList());
//
//        List<SubjectResponseSimple> subjects = subjectService.getAllByOrderByName().stream()
//                .map(SubjectResponseSimple::new)
//                .collect(Collectors.toList());
        long amountOfStudents = studentService.findAllOrderedByName().size();

        return new HomeResponse(
                amountOfStudents
        );
    }
}
