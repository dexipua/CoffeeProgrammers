package com.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.config.JWT.JwtUtils;
import com.school.dto.UserNewsResponse;
import com.school.models.User;
import com.school.models.UserNews;
import com.school.service.impl.UserNewsServiceImpl;
import com.school.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserNewsController.class)
class UserNewsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private UserNewsServiceImpl userNewsService;

    private User user;

    private UserNews userNews;
    private UserNews userNews2;

    @BeforeEach
    void setUp() {
        user = new User("Vlad", "Bulakovskyi", "vlad@gmail.com", "passWord1");
        user.setId(1);

        userNews = new UserNews("New mark 10", user);
        userNews2 = new UserNews("The mark has been changed to 9", user);
    }

    @Test
    @WithMockUser(username = "vlad@gmail.com", roles = "STUDENT")
    void getAllNewsByUserId() throws Exception {
        when(userNewsService.getAllNewsByUserId(user.getId())).thenReturn(List.of(userNews, userNews2));
        when(userService.findByEmail("vlad@gmail.com")).thenReturn(user);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/student_news/myNews"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<UserNewsResponse> expectedResponseBody = new ArrayList<>(Arrays.asList(new UserNewsResponse(userNews), new UserNewsResponse(userNews2)));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }
}