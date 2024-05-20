package com.school.controller;

import com.school.dto.SubjectRequest;
import com.school.dto.SubjectResponse;
import com.school.dto.TransformSubject;
import com.school.models.Subject;
import com.school.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/subject")
@RestController
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody SubjectRequest subjectRequest) {
        try { //TODO
            subjectService.findByName(subjectRequest.getName());
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Subject with this name is present");
        } catch (IllegalArgumentException e) {

        }

        Subject subject = TransformSubject.transformFromRequestToModel(subjectRequest);
        subjectService.create(subject);
    }

    @GetMapping("/byId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponse getById(@PathVariable("id") long id) {
        return new SubjectResponse(subjectService.readById(id));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") long id, @RequestBody SubjectRequest subjectRequest) {
        Subject subjectToUpdate = TransformSubject.transformFromRequestToModel(subjectRequest);
        subjectToUpdate.setId(id);
        subjectService.update(subjectToUpdate);

    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {
        subjectService.delete(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponse> getAllByOrderByName() {
        List<SubjectResponse> result = new ArrayList<>();
        for (Subject subject : subjectService.getAllByOrderByName()) {
            result.add(new SubjectResponse(subject));
        }
        return result;
    }

    @GetMapping("/byName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponse getByName(@PathVariable("name") String name) {
        return new SubjectResponse(subjectService.findByName(name));
    }

    @GetMapping("/byTeacherId/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponse> getByTeacherId(@PathVariable("teacher_id") long teacherId) {
        List<SubjectResponse> result = new ArrayList<>();
        for (Subject subject : subjectService.findByStudent_Id(teacherId)) {
            result.add(new SubjectResponse(subject));
        }
        return result;
    }

    @GetMapping("/byStudentId/{student_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponse> getByStudentId(@PathVariable("student_id") long studentId) {
        List<SubjectResponse> result = new ArrayList<>();
        for (Subject subject : subjectService.findByStudent_Id(studentId)) {
            result.add(new SubjectResponse(subject));
        }
        return result;
    }
}
