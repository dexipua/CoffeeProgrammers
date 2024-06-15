package com.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.config.JWT.JwtUtils;
import com.school.dto.student.StudentResponseAll;
import com.school.dto.student.StudentResponseSimple;
import com.school.dto.student.StudentResponseWithEmail;
import com.school.dto.user.UserRequestCreate;
import com.school.dto.user.UserRequestUpdate;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.models.User;
import com.school.service.StudentService;
import com.school.service.UserNewsService;
import com.school.service.impl.MarkServiceImpl;
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

import static com.school.controller.TeacherControllerTest.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private MarkServiceImpl markService;

    @MockBean
    private UserNewsService userNewsService;

    private UserRequestCreate request;
    private Student student;
    private Student student2;

    private Student updated;

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

        student = new Student(new User("Vlad", "Bulakovskyi", "vlad@gmail.com", "passWord1"));
        student.setId(1);

        student2 = new Student(new User("Vlad2", "Bulakovskyi2", "vlad2@gmail.com", "passWord1"));
        student2.setId(2);

        updated = new Student(new User("Rename", "Surname", "vlad@gmail.com", "NewPassword111"));
        updated.setId(1);
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void createStudent() throws Exception {
        when(studentService.create(request)).thenReturn(student);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .post("/students/create")
                        .with(csrf())
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        StudentResponseSimple expectedResponseBody = new StudentResponseSimple(student);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getStudentById() throws Exception {
        when(studentService.findById(student.getId())).thenReturn(student);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/students/getById/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        StudentResponseAll expectedResponseBody = new StudentResponseAll(student, 0);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void updateStudent() throws Exception {
        studentService.create(request);

        when(studentService.findById(student.getId())).thenReturn(updated);
        when(studentService.update(student.getId(), requestUpdate)).thenReturn(updated);
        //then
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .put("/students/update/1")
                        .with(csrf())
                        .content(asJsonString(requestUpdate))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        StudentResponseAll expectedResponseBody = new StudentResponseAll(updated, 0);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));

    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void deleteStudent() throws Exception {
        studentService.create(request);

        when(studentService.findById(student.getId())).thenReturn(student);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/students/delete/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getAllStudents() throws Exception {
        when(studentService.findAllOrderedByName()).thenReturn(List.of(student, student2));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/students/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<StudentResponseWithEmail> expectedResponseBody = new ArrayList<>(Arrays.asList(new StudentResponseWithEmail(student), new StudentResponseWithEmail(student2)));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));

    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getStudentsByTeacherId() throws Exception {
        Subject subject = new Subject();
        subject.setName("Math");
        subject.setTeacher(new Teacher());
        student.setSubjects(Set.of(subject));
        studentService.create(request);

        when(studentService.findStudentsByTeacherId(1)).thenReturn(List.of(student));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/students/getAllByTeacherId/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<StudentResponseWithEmail> expectedResponseBody = new ArrayList<>(List.of(new StudentResponseWithEmail(student)));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getByName() throws Exception {
        when(studentService.findAllByUser_FirstNameAndUser_LastName("Vlad", "Bulakovskyi")).thenReturn(List.of(student));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/students/getAllByName/")
                        .with(csrf())
                        .param("first_name", "Vlad")
                        .param("last_name", "Bulakovskyi")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<StudentResponseSimple> expectedResponseBody = List.of(new StudentResponseSimple(student));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getStudentsCount() throws Exception {
        when(studentService.getStudentsCount()).thenReturn((long) 2);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/students/count")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long expectedResponseBody = (long) 2;
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }
    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void findAllBySubjectsIdIsNot() throws Exception{
        student.setSubjects(new HashSet<>(Set.of(subject)));
        student2.setSubjects(new HashSet<>(Set.of(subject2)));
        List<Student> students = List.of(student, student2);


        when(studentService.findAllBySubjectsIdIsNot(subject2.getId())).thenReturn(List.of(student));


        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/students/subjectIdIsNot/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<StudentResponseWithEmail> expectedResponseBody = new ArrayList<>(Arrays.asList(new StudentResponseWithEmail(student)));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }
}