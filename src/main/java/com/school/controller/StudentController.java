package com.school.controller;

import com.school.dto.StudentRequest;
import com.school.dto.StudentResponse;
import com.school.models.Student;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    @Autowired
    private final StudentService studentService;

    @Autowired
    private final SubjectService subjectService;

    @GetMapping
    public List<StudentResponse> getAll() {
        List<StudentResponse> result = new ArrayList<>();
        for (Student student : studentService.findAll()) {
            result.add(new StudentResponse(student));
        }
        return result;
    }

    @PostMapping("/create")
    public StudentResponse createStudent(@RequestBody StudentRequest studentRequest) {
        Student student = new Student(studentRequest.getUser());
        Student createdStudent = studentService.create(student);
        return new StudentResponse(createdStudent);
    }

    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable("id") long id) {
        Student student = studentService.findById(id);
        return new StudentResponse(student);
    }

    @PutMapping("/update")
    public StudentResponse updateStudent(@RequestBody StudentRequest studentRequest) {
        Student student = studentService.findById(studentRequest.getId());
        student.setUser(studentRequest.getUser());
        Student updatedStudent = studentService.update(student);
        return new StudentResponse(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") long id) {
        studentService.deleteById(id);
    }

    @GetMapping("/bySubject/{subjectName}")
    public List<StudentResponse> getStudentsBySubjectName(@PathVariable("subjectName") String subjectName) {
        List<Student> students = studentService.findBySubjectName(subjectName);
        List<StudentResponse> responses = new ArrayList<>();
        for (Student student : students) {
            responses.add(new StudentResponse(student));
        }
        return responses;
    }

    @GetMapping("/byUsername/{username}")
    public StudentResponse getStudentByUsername(@PathVariable("username") String username) {
        Student student = studentService.findByUsername(username);
        return new StudentResponse(student);
    }

    @GetMapping("/byEmail/{email}")
    public StudentResponse getStudentByEmail(@PathVariable("email") String email) {
        Student student = studentService.findByEmail(email);
        return new StudentResponse(student);
    }
}