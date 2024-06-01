package com.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.config.JWT.JwtUtils;
import com.school.dto.teacher.TeacherRequest;
import com.school.dto.teacher.TeacherResponseAll;
import com.school.dto.teacher.TeacherResponseToGet;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.models.User;
import com.school.service.TeacherService;
import com.school.service.impl.UserServiceImpl;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserServiceImpl userService;

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void createTeacher() throws Exception{
        TeacherRequest request = new TeacherRequest("Vlad", "Bulakovskyi", "vlad@gmail.com", "passWord1");
        Teacher teacher = new Teacher(new User("Vlad", "Bulakovskyi", "vlad@gmail.com", "passWord1"));

        when(teacherService.create(TeacherRequest.toTeacher(request))).thenReturn(teacher);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .post("/teachers/create")
                    .with(csrf())
                    .content(asJsonString(request))
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        TeacherResponseToGet expectedResponseBody = new TeacherResponseToGet(teacher);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getTeacherById() throws Exception{
        Teacher t1 = new Teacher(new User("Vlad", "Bulakovskyi", "vlad@gmail.com", "passWord1"));

        when(teacherService.findById(1L)).thenReturn(t1);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/teachers/getById/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        TeacherResponseAll expectedResponseBody = new TeacherResponseAll(t1);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                               new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getAllTeachers() throws Exception{
        Teacher t1 = new Teacher(new User("Vlad", "Bulakovskyi", "vlad@gmail.com", "passWord1"));
        Teacher t2 = new Teacher(new User("Vlad2", "Bulakovskyi2", "vlad2@gmail.com", "passWord1"));

        when(teacherService.findAll()).thenReturn(List.of(t1, t2));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                    .get("/teachers/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<TeacherResponseToGet> expectedResponseBody = new ArrayList<>(Arrays.asList(new TeacherResponseToGet(t1), new TeacherResponseToGet(t2)));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void updateTeacher() throws Exception{
        //given
        Teacher t1 = new Teacher(new User("Vlad", "Bulakovskyi", "vlad@gmail.com", "passWord1"));
        t1.setId(1L);
        teacherService.create(t1);

        TeacherRequest request = new TeacherRequest("Rename", "Surname", "newEmail@gmail.com", "NewPassword111");
        Teacher updated = new Teacher(new User("Rename", "Surname", "newEmail@gmail.com", "NewPassword111"));
        updated.setId(1L);
        when(teacherService.findById(1L)).thenReturn(updated);
        when(teacherService.update(updated)).thenReturn(updated);
        //then
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .put("/teachers/update/1")
                        .with(csrf())
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        //when

        TeacherResponseAll expectedResponseBody = new TeacherResponseAll(updated);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void deleteTeacher() throws Exception{
        Teacher t1 = new Teacher(new User("Rename", "Surname", "newEmail@gmail.com", "NewPassword111"));
        t1.setId(1L);
        teacherService.create(t1);

        when(teacherService.findById(1L)).thenReturn(t1);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/teachers/delete/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getTeacherBySubjectName() throws Exception{
        Teacher t1 = new Teacher(new User("Vlad", "Bulakovskyi", "vlad@gmail.com", "passWord1"));
        t1.addSubject(new Subject("Maths"));
        teacherService.create(t1);

        when(teacherService.findBySubjectName("Maths")).thenReturn(List.of(t1));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/teachers/getAllBySubjectName/?subject_name=Maths"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<TeacherResponseToGet> expectedResponseBody = new ArrayList<>(Arrays.asList(new TeacherResponseToGet(t1)));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}