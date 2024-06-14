package com.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.config.JWT.JwtUtils;
import com.school.dto.home.HomeResponse;
import com.school.dto.subject.SubjectResponseSimple;
import com.school.dto.teacher.TeacherResponseSimple;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.models.User;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import com.school.service.TeacherService;
import com.school.service.impl.MarkServiceImpl;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private SubjectService subjectService;
    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private MarkServiceImpl markService;

    private Student student;
    private Student student2;

    private Teacher teacher;
    private Teacher teacher2;

    private Subject subject;
    private Subject subject2;


    @BeforeEach
    void setUp() {
        student = new Student(new User("Vlad", "Bulakovskyi", "vlad@gmail.com", "passWord1"));
        student.setId(1);

        student2 = new Student(new User("Vlad2", "Bulakovskyi2", "vlad2@gmail.com", "passWord1"));
        student2.setId(2);

        teacher = new Teacher(new User("Peter", "West", "Peter@gmail.com", "passWord1"));
        teacher.setId(1);

        teacher2 = new Teacher(new User("Peter2", "West", "Peter2@gmail.com", "passWord1"));
        teacher2.setId(2);

        subject = new Subject();
        subject.setName("Math");
        subject.setId(1);

        subject2 = new Subject();
        subject2.setName("Philosophy");
        subject2.setId(2);
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void homePage() throws Exception{
        when(teacherService.findAll()).thenReturn(List.of(teacher, teacher2));
        when(studentService.findAllOrderedByName()).thenReturn(List.of(student, student2));
        when(subjectService.getAllByOrderByName()).thenReturn(List.of(subject, subject2));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        HomeResponse expectedResponseBody = new HomeResponse(List.of(new TeacherResponseSimple(teacher), new TeacherResponseSimple(teacher2)), List.of(new SubjectResponseSimple(subject), new SubjectResponseSimple(subject2)), 2);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }
}