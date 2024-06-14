package com.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.config.JWT.JwtUtils;
import com.school.dto.teacher.TeacherResponseAll;
import com.school.dto.teacher.TeacherResponseSimple;
import com.school.dto.user.UserRequestCreate;
import com.school.dto.user.UserRequestUpdate;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.models.User;
import com.school.service.StudentService;
import com.school.service.TeacherService;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserServiceImpl userService;

    private UserRequestCreate request;
    private Teacher teacher;
    private Teacher teacher2;

    private Teacher updated;

    private Student student;
    private Student student2;

    private Subject subject;
    private Subject subject2;

    private UserRequestUpdate requestUpdate;

    @BeforeEach
    void setUp() {
        subject = new Subject();
        subject.setName("Math");
        subject.setId(1);

        subject2 = new Subject();
        subject2.setName("Philosophy");
        subject2.setId(2);

        request = new UserRequestCreate();
        request.setFirstName("Vlad");
        request.setLastName("Bulakovskyi");
        request.setEmail("vlad@gmail.com");
        request.setPassword("passWord1");

        requestUpdate = new UserRequestUpdate();
        requestUpdate.setFirstName("Rename");
        requestUpdate.setLastName("Surname");
        requestUpdate.setPassword("NewPassword111");

        teacher = new Teacher(new User("Vlad", "Bulakovskyi", "vlad@gmail.com", "passWord1"));
        teacher.setId(1);

        teacher2 = new Teacher(new User("Vlad2", "Bulakovskyi2", "vlad2@gmail.com", "passWord1"));
        teacher2.setId(2);

        student = new Student(new User("Peter", "Kornienko", "vlad2@gmail.com", "passWord1"));
        student.setId(1);

        student2 = new Student(new User("Peter2", "Kornienko2", "vlad2@gmail.com", "passWord1"));
        student2.setId(2);

        updated = new Teacher(new User("Rename", "Surname", "vlad@gmail.com", "NewPassword111"));
        updated.setId(1);
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void createTeacher() throws Exception{
        when(teacherService.create(request)).thenReturn(teacher);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .post("/teachers/create")
                    .with(csrf())
                    .content(asJsonString(request))
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        TeacherResponseSimple expectedResponseBody = new TeacherResponseSimple(teacher);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getByName() throws Exception{
        when(teacherService.findAllByUser_FirstNameAndAndUser_LastName("Vlad", "Bulakovskyi")).thenReturn(List.of(teacher));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/teachers/getAllByName/")
                        .with(csrf())
                        .param("first_name", "Vlad")
                        .param("last_name", "Bulakovskyi")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<TeacherResponseSimple> expectedResponseBody = List.of(new TeacherResponseSimple(teacher));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getTeacherById() throws Exception{
        when(teacherService.findById(teacher.getId())).thenReturn(teacher);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/teachers/getById/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        TeacherResponseAll expectedResponseBody = new TeacherResponseAll(teacher, List.of());
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                               new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getAllTeachers() throws Exception{
        when(teacherService.findAll()).thenReturn(List.of(teacher, teacher2));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                    .get("/teachers/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<TeacherResponseSimple> expectedResponseBody = new ArrayList<>(Arrays.asList(new TeacherResponseSimple(teacher), new TeacherResponseSimple(teacher2)));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void updateTeacher() throws Exception{
        teacherService.create(request);

        when(teacherService.findById(teacher.getId())).thenReturn(updated);
        when(teacherService.update(teacher.getId(), requestUpdate)).thenReturn(updated);
        //then
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .put("/teachers/update/1")
                        .with(csrf())
                        .content(asJsonString(requestUpdate))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        TeacherResponseAll expectedResponseBody = new TeacherResponseAll(updated, List.of());
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void deleteTeacher() throws Exception{
        teacherService.create(request);

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/teachers/delete/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getTeacherBySubjectName() throws Exception{
        Subject subject = new Subject();
        subject.setName("Math");
        teacher.setSubjects(Set.of(subject));
        teacherService.create(request);

        when(teacherService.findBySubjectName("Maths")).thenReturn(List.of(teacher));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/teachers/getAllBySubjectName/?subject_name=Maths"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<TeacherResponseSimple> expectedResponseBody = new ArrayList<>(List.of(new TeacherResponseSimple(teacher)));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }
    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getAllByStudentId() throws Exception{
        student.setSubjects(new HashSet<>(Set.of(subject)));
        student2.setSubjects(new HashSet<>(Set.of(subject2)));
        teacher.setSubjects(new HashSet<>(Set.of(subject, subject2)));
        List<Student> students = List.of(student, student2);


        when(teacherService.findAllByStudentId(1)).thenReturn(List.of(teacher, teacher2));


        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/teachers/getByStudentId/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<TeacherResponseSimple> expectedResponseBody = new ArrayList<>(Arrays.asList(new TeacherResponseSimple(teacher), new TeacherResponseSimple(teacher2)));
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