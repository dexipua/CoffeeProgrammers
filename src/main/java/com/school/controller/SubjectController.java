package com.school.controller;

import com.school.dto.SubjectRequest;
import com.school.dto.SubjectResponse;
import com.school.models.Subject;
import com.school.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestMapping(value = "/subject")
@RestController
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody SubjectRequest subjectRequest) {
        subjectService.create(new Subject(subjectRequest.getName()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponse getById(@PathVariable("id") long id) {
        return new SubjectResponse(subjectService.readById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") long id, @RequestBody SubjectRequest subjectRequest) {
        if (Objects.equals(id, subjectRequest.getId())) {
            subjectService.update(new Subject(subjectRequest.getName()));
        } else {
            throw new IllegalStateException("Invalid subject id");
        }
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        subjectService.delete(id);
    }

    @GetMapping(value = "/allByOrderByName", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponse> getAllByOrderByName() {
        List<SubjectResponse> result = new ArrayList<>();
        for (Subject subject : subjectService.getAllByOrderByName()) {
            result.add(new SubjectResponse(subject));
        }
        return result;
    }

    @GetMapping(value = "/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponse getByName(@PathVariable("name") String name) {
        return new SubjectResponse(subjectService.findByName(name));
    }

    @GetMapping(value = "/{teacher_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponse> getByTeacherId(@PathVariable("teacher_id") long teacherId) {
        List<SubjectResponse> result = new ArrayList<>();
        for (Subject subject : subjectService.findByStudent_Id(teacherId)) {
            result.add(new SubjectResponse(subject));
        }
        return result;
    }

    @GetMapping(value = "/{student_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponse> getByStudentId(@PathVariable("student_id") long studentId) {
        List<SubjectResponse> result = new ArrayList<>();
        for (Subject subject : subjectService.findByStudent_Id(studentId)) {
            result.add(new SubjectResponse(subject));
        }
        return result;
    }
}
