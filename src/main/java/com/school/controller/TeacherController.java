package com.school.controller;

import com.school.dto.TeacherRequest;
import com.school.dto.TeacherResponseAll;
import com.school.dto.TeacherResponseToGet;
import com.school.models.Teacher;
import com.school.service.TeacherService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping("/addTeacher")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    public TeacherResponseToGet create(@RequestBody TeacherRequest teacherRequest){
        return new TeacherResponseToGet(teacherService.create(TeacherRequest.toTeacher(teacherRequest)));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or @userSecurity.checkUserByTeacher(#auth, #id)")
    public TeacherResponseAll update(@RequestBody TeacherRequest teacherRequest, @PathVariable("id") long id, Authentication auth){
        Teacher teacher = teacherService.findById(id);
        teacher.getUser().setEmail(teacherRequest.getEmail());
        teacher.getUser().setPassword(teacherRequest.getPassword());
        teacher.getUser().setFirstName(teacherRequest.getFirstName());
        teacher.getUser().setLastName(teacherRequest.getLastName());
        return new TeacherResponseAll(teacherService.update(teacher));
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<TeacherResponseToGet> getAll(){
        List<TeacherResponseToGet> result = new ArrayList<>();
        for(Teacher teacher : teacherService.findAllByOrderByUser()){
            result.add(new TeacherResponseToGet(teacher));
        }
        return result;
    }

    @GetMapping("/get/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherResponseAll getById(@PathVariable("teacher_id") long teacher_id){
        return new TeacherResponseAll(teacherService.findById(teacher_id));
    }

    @GetMapping("/getBySubjectName/{subject_name}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherResponseAll getBySubjectName(@PathVariable("subject_name") String subjectName){
        Teacher teacher = teacherService.findBySubjectName(subjectName);
        return new TeacherResponseAll(teacher);
    }

    @DeleteMapping("/delete/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    public void deleteById(@PathVariable("teacher_id") long teacher_id){
        teacherService.delete(teacher_id);
    }
}
