package com.school.controller;

import com.school.dto.HttpResponse;
import com.school.dto.TeacherRequest;
import com.school.dto.TeacherResponse;
import com.school.models.Teacher;
import com.school.models.User;
import com.school.repositories.RoleRepository;
import com.school.service.RoleService;
import com.school.service.TeacherService;
import com.school.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @GetMapping
    public List<TeacherResponse> getAll(){
        List<TeacherResponse> result = new ArrayList<>();
        for(Teacher teacher : teacherService.findAll()){
            result.add(new TeacherResponse(teacher));
        }
        return result;
    }

    @GetMapping("/get/{teacher_id}")
    public ResponseEntity<?> getById(@PathVariable("teacher_id") long teacher_id){
        try {
            return new ResponseEntity<>(new TeacherResponse(teacherService.findById(teacher_id)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new HttpResponse(HttpStatus.NOT_FOUND.value(),
                    "Teacher with id " + teacher_id + " not found"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{teacher_id}/update")
    public ResponseEntity<?> update(@RequestBody TeacherRequest teacherRequest, @PathVariable("teacher_id") long id){
        Teacher teacher = teacherService.findById(id);
        try {
            if (!teacherService.findByUsername(teacherRequest.getUsername()).equals(teacher)) {
                return new ResponseEntity<>(new HttpResponse(HttpStatus.BAD_REQUEST.value(),
                        "Teacher with username " + teacherRequest.getUsername() + " already exists"),
                        HttpStatus.BAD_REQUEST);
            }
        } catch(EntityNotFoundException e){
            teacher.getUser().setUsername(teacherRequest.getUsername());
        }
        try {
            if (!teacherService.findByEmail(teacherRequest.getEmail()).equals(teacher)) {
                return new ResponseEntity<>(new HttpResponse(HttpStatus.BAD_REQUEST.value(),
                        "Teacher with email " + teacherRequest.getEmail() + " already exists"),
                        HttpStatus.BAD_REQUEST);
            }
        } catch(EntityNotFoundException e){
            teacher.getUser().setEmail(teacherRequest.getEmail());
        }
        teacher.getUser().setFirstName(teacherRequest.getFirstName());
        teacher.getUser().setLastName(teacherRequest.getLastName());
        return new ResponseEntity<>(new TeacherResponse(teacherService.update(teacher)), HttpStatus.OK);
    }

    @PostMapping("/addTeacher")
    public ResponseEntity<?> create(@RequestBody TeacherRequest teacherRequest){
        try {
            teacherService.findByUsername(teacherRequest.getUsername());
            return new ResponseEntity<>(new HttpResponse(HttpStatus.NOT_FOUND.value(),
                    "Teacher with username " + teacherRequest.getUsername() + " already exists"),
                    HttpStatus.NOT_FOUND);
        } catch(EntityNotFoundException e) {}
        Teacher teacher;
        try {
            teacher = TeacherRequest.toTeacher(teacherRequest);
            teacher.getUser().setRole(roleService.findByName("TEACHER"));
            teacherService.create(teacher);
        } catch(ConstraintViolationException e) {
            return new ResponseEntity<>(new HttpResponse(HttpStatus.BAD_REQUEST.value(),
                    e.getConstraintViolations().toString()),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(teacherRequest, HttpStatus.CREATED);
    }

    @GetMapping("/getBySubjectName/{subject_name}")
    public ResponseEntity<?> getBySubjectName(@PathVariable("subject_name") String subjectName){
        Teacher teacher;
        try {
            teacher = teacherService.findBySubjectName(subjectName);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new HttpResponse(HttpStatus.NOT_FOUND.value(),
                    "Teacher with subject " + subjectName + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new TeacherResponse(teacher), HttpStatus.OK);
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable("username") String username){
        Teacher teacher;
        try {
            teacher = teacherService.findByUsername(username);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new HttpResponse(HttpStatus.NOT_FOUND.value(),
                    "Teacher with username " + username + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new TeacherResponse(teacher), HttpStatus.OK);
    }

    @DeleteMapping("/{teacher_id}/delete")
    public ResponseEntity<?> deleteById(@PathVariable("teacher_id") long teacher_id){
        if(Objects.equals(teacherService.findById(teacher_id).getUser().getRole().getAuthority(), "ROLE_CHIEF_TEACHER")){
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Cannot delete teacher with id " + teacher_id);
        }
        User user = teacherService.findById(teacher_id).getUser();
        user.setUsername("None");
        user.setPassword("noneNone1234567890");
        user.setEmail("none@go.co");
        teacherService.update(teacherService.findById(teacher_id));
        return new ResponseEntity<>(new HttpResponse(HttpStatus.OK.value(),
                "Deleted"),
                HttpStatus.OK);
    }
}
