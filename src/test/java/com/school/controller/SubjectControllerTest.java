package com.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.config.JWT.JwtUtils;
import com.school.dto.subject.SubjectRequest;
import com.school.dto.subject.SubjectResponseAll;
import com.school.dto.subject.SubjectResponseSimple;
import com.school.dto.subject.SubjectResponseWithTeacher;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.models.User;
import com.school.service.impl.*;
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

import java.util.*;

import static com.school.controller.TeacherControllerTest.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubjectController.class)
class SubjectControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MarkServiceImpl markService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private SubjectServiceImpl subjectService;

    @MockBean
    private TeacherServiceImpl teacherService;

    @MockBean
    private StudentServiceImpl studentService;

    private Subject subject;
    private Subject subject2;

    private Subject updated;

    private SubjectRequest request;
    private SubjectRequest request2;

    private Teacher teacher;
    private Student student;

    @BeforeEach
    void setUp() {
        subject = new Subject();
        subject.setName("Math");
        subject.setId(1);

        subject2 = new Subject();
        subject2.setName("Philosophy");
        subject2.setId(2);

        updated = new Subject();
        updated.setName("Philosophy");
        updated.setId(1);

        request = new SubjectRequest();
        request.setName("Math");

        request2 = new SubjectRequest();
        request2.setName("Philosophy");

        teacher = new Teacher(new User("Vlad", "Bulakovskyi", "vlad1@gmail.com", "Vlad123"));
        teacher.setId(1);

        student = new Student(new User("Vlad", "Bulakovskyi", "vlad2@gmail.com", "Vlad123"));
        student.setId(1);
    }
    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void create() throws Exception{
        when(subjectService.create(request)).thenReturn(subject);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .post("/subjects/create")
                        .with(csrf())
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        SubjectResponseSimple expectedResponseBody = new SubjectResponseSimple(subject);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getById() throws Exception {
        when(subjectService.findById(teacher.getId())).thenReturn(subject);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/subjects/getById/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        SubjectResponseAll expectedResponseBody = new SubjectResponseAll(subject);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void update() throws Exception {
        subjectService.create(request);

        when(subjectService.findById(subject.getId())).thenReturn(updated);
        when(subjectService.update(subject.getId(), request2)).thenReturn(updated);
        //then
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .put("/subjects/update/1")
                        .with(csrf())
                        .content(asJsonString(request2))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        SubjectResponseAll expectedResponseBody = new SubjectResponseAll(updated);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void delete() throws Exception {
        subjectService.create(request);

        when(subjectService.findById(subject.getId())).thenReturn(subject);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/subjects/delete/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getAllByOrderByName() throws Exception {
        when(subjectService.getAllByOrderByName()).thenReturn(List.of(subject, subject2));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/subjects/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<SubjectResponseWithTeacher> expectedResponseBody = new ArrayList<>(Arrays.asList(new SubjectResponseWithTeacher(subject), new SubjectResponseWithTeacher(subject2)));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getByNameContaining() throws Exception {
        when(subjectService.findByNameContaining("Math")).thenReturn(List.of(subject));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/subjects/getAllByName/")
                        .with(csrf())
                        .param("subject_name", "Math")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<SubjectResponseWithTeacher> expectedResponseBody = List.of(new SubjectResponseWithTeacher(subject));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getByTeacherId() throws Exception {
        subject.setTeacher(teacher);
        subjectService.create(request);

        when(subjectService.findByTeacher_Id(1)).thenReturn(List.of(subject));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/subjects/getAllByTeacherId/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<SubjectResponseWithTeacher> expectedResponseBody = new ArrayList<>(List.of(new SubjectResponseWithTeacher(subject)));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void setTeacher() throws Exception {
        subjectService.create(request);

        when(subjectService.findById(subject.getId())).thenReturn(subject);
        when(teacherService.findById(teacher.getId())).thenReturn(teacher);

        mvc.perform(MockMvcRequestBuilders
                        .patch("/subjects/update/1/setTeacher/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void deleteTeacher() throws Exception {
        subjectService.create(request);

        when(subjectService.findById(subject.getId())).thenReturn(subject);
        when(teacherService.findById(teacher.getId())).thenReturn(teacher);

        mvc.perform(MockMvcRequestBuilders
                        .patch("/subjects/update/1/deleteTeacher")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getByStudentId() throws Exception {
        subject.setStudents(new HashSet<>(Set.of(student)));
        subjectService.create(request);

        when(subjectService.findByStudent_Id(1)).thenReturn(List.of(subject));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/subjects/getAllByStudentId/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<SubjectResponseWithTeacher> expectedResponseBody = new ArrayList<>(List.of(new SubjectResponseWithTeacher(subject)));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void addStudent() throws Exception {
        subjectService.create(request);

        when(subjectService.findById(subject.getId())).thenReturn(subject);
        when(studentService.findById(student.getId())).thenReturn(student);

        mvc.perform(MockMvcRequestBuilders
                        .patch("/subjects/update/1/addStudent/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void deleteStudent() throws Exception {
        subjectService.create(request);

        when(subjectService.findById(subject.getId())).thenReturn(subject);
        when(studentService.findById(student.getId())).thenReturn(student);

        mvc.perform(MockMvcRequestBuilders
                        .patch("/subjects/update/1/deleteStudent/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}