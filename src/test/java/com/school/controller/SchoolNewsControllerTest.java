package com.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.config.JWT.JwtUtils;
import com.school.dto.schoolNews.SchoolNewsRequest;
import com.school.dto.schoolNews.SchoolNewsResponse;
import com.school.models.SchoolNews;
import com.school.service.impl.SchoolNewsServiceImpl;
import com.school.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.school.controller.TeacherControllerTest.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SchoolNewsController.class)
class SchoolNewsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private SchoolNewsServiceImpl schoolNewsService;

    private SchoolNews schoolNews;
    private SchoolNews schoolNews2;

    @BeforeEach
    void setUp() {
        schoolNews = new SchoolNews("You needn`t go to school tomorrow");
        schoolNews.setId(1);

        schoolNews2 = new SchoolNews("The fair will be held tomorrow at 11 o'clock");
        schoolNews2.setId(2);
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getAllNews() throws Exception {
        when(schoolNewsService.getAllSchoolNews()).thenReturn(List.of(schoolNews, schoolNews2));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/schoolNews/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<SchoolNewsResponse> expectedResponseBody = new ArrayList<>(Arrays.asList(new SchoolNewsResponse(schoolNews), new SchoolNewsResponse(schoolNews2)));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void delete() throws Exception {
        schoolNewsService.create(schoolNews);

        when(schoolNewsService.findById(schoolNews.getId())).thenReturn(schoolNews);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/schoolNews/delete/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void create() throws Exception{
        when(schoolNewsService.create(any(SchoolNews.class))).thenReturn(schoolNews);
        SchoolNewsRequest request = new SchoolNewsRequest();
        request.setText("You needn`t go to school tomorrow");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .post("/schoolNews/create")
                        .with(csrf())
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        SchoolNewsResponse expectedResponseBody = new SchoolNewsResponse(schoolNews);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }
}