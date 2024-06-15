package com.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.config.JWT.JwtUtils;
import com.school.dto.mark.MarkRequest;
import com.school.dto.mark.MarkResponseAll;
import com.school.dto.mark.MarkResponseForSubject;
import com.school.models.Mark;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.User;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.school.controller.TeacherControllerTest.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MarkController.class)
class MarkControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MarkServiceImpl markService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserServiceImpl userService;

    private Mark mark;

    private Mark updated;

    private Student student;

    private Subject subject;

    private MarkRequest request;

    private MarkRequest requestUpdate;

    @BeforeEach
    void setUp() {
        student = new Student(new User("Vlad", "Bulakovskyi", "vlad@gmail.com", "passWord1"));
        student.setId(1);

        subject = new Subject();
        subject.setName("Math");
        subject.setId(1);

        mark = new Mark(10, student, subject);
        mark.setId(1);

        request = new MarkRequest();
        request.setValue(10);

        requestUpdate = new MarkRequest();
        requestUpdate.setValue(2);

        updated = new Mark(10, student, subject);
        updated.setId(1);
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void create() throws Exception{
        when(markService.create(request, subject.getId(), student.getId())).thenReturn(mark);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .post("/marks/subject/1/student/1/createMark")
                        .with(csrf())
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        MarkResponseAll expectedResponseBody = new MarkResponseAll(mark);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void update() throws Exception{
        markService.create(request, subject.getId(), student.getId());

        when(markService.findById(mark.getId())).thenReturn(updated);
        when(markService.update(mark.getId(), requestUpdate)).thenReturn(updated);
        //then
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .put("/marks/update/1")
                        .with(csrf())
                        .content(asJsonString(requestUpdate))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MarkResponseAll expectedResponseBody = new MarkResponseAll(updated);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void delete() throws Exception{
        markService.create(request, subject.getId(), student.getId());

        when(markService.findById(mark.getId())).thenReturn(mark);

        mvc.perform(MockMvcRequestBuilders
                        .delete("/marks/delete/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CHIEF_TEACHER")
    void getAllBySubjectId() throws Exception{
        markService.create(request, subject.getId(), student.getId());

        when(markService.findAllBySubjectId(1)).thenReturn(new HashMap<>(Map.of(student, List.of(mark))));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/marks/getAllBySubjectId/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<MarkResponseForSubject> expectedResponseBody = new ArrayList<>(List.of(new MarkResponseForSubject(student, List.of(mark))));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                new ObjectMapper().writeValueAsString(expectedResponseBody));
    }
}