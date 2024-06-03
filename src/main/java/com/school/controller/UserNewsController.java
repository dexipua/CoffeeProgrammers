package com.school.controller;

import com.school.dto.UserNewsResponse;
import com.school.models.UserNews;
import com.school.service.UserNewsService;
import com.school.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student_news")
public class UserNewsController {
    private final UserNewsService newsService;
    private final UserService userService;

    @GetMapping("/myNews")
    @ResponseStatus(HttpStatus.OK)
    public List<UserNewsResponse> getAllNewsByStudentId(Authentication authentication) {
        List<UserNews> news = newsService.getAllNewsByUserId(
                userService.findByEmail(
                ((UserDetails)authentication.getPrincipal()).getUsername()).getId()
        );
        return news.stream()
                .map(UserNewsResponse::new)
                .collect(Collectors.toList());
    }
}
