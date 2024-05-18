package com.school.controller;

import com.school.dto.TeacherResponse;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.service.SubjectService;
import com.school.service.TeacherService;
import com.school.service.impl.TeacherServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @Autowired
    SubjectService subjectService;

    @GetMapping
    public List<TeacherResponse> getAll(){
        List<TeacherResponse> result = new ArrayList<>();
        for(Teacher teacher : teacherService.findAll()){
            result.add(new TeacherResponse(teacher));
        }
        return result;
    }

    @GetMapping("/{teacher-id}")
    public TeacherResponse getById(@PathVariable("teacher-id") long teacher_id){
        return new TeacherResponse(teacherService.readById(teacher_id));
    }

    @PutMapping("/{teacher-id}/update")
    public TeacherResponse update(@RequestBody Teacher teacher, @PathVariable("teacher-id") long id){
        return new TeacherResponse(teacherService.update(teacher));
    }
}
