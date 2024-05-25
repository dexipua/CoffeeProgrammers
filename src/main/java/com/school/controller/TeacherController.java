package com.school.controller;

import com.school.dto.TeacherRequest;
import com.school.dto.TeacherResponseAll;
import com.school.dto.TeacherResponseToGet;
import com.school.models.Teacher;
import com.school.service.TeacherService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
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
    public TeacherResponseAll create(@RequestBody TeacherRequest teacherRequest){
        return new TeacherResponseAll(TeacherRequest.toTeacher(teacherRequest));
    }

    @PutMapping("/update/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherResponseAll update(@RequestBody TeacherRequest teacherRequest, @PathVariable("teacher_id") long id){
        Teacher teacher = teacherService.findById(id);
        teacher.getUser().setFirstName(teacherRequest.getFirstName());
        teacher.getUser().setLastName(teacherRequest.getLastName());
        return new TeacherResponseAll(teacherService.update(teacher));
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<TeacherResponseToGet> getAll(){
        List<TeacherResponseToGet> result = new ArrayList<>();
        for(Teacher teacher : teacherService.findAll()){
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
    public void deleteById(@PathVariable("teacher_id") long teacher_id){
        Teacher teacher = teacherService.findById(teacher_id);
        teacher.setSubjects(null);
        teacherService.delete(teacher_id);
    }
}
