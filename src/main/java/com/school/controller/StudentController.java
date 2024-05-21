package com.school.controller;

import com.school.dto.StudentRequest;
import com.school.dto.StudentResponse;
import com.school.models.Student;
import com.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest studentRequest) {
        Student student = StudentRequest.toStudent(studentRequest);
        Student createdStudent = studentService.create(student);
        return ResponseEntity.ok(new StudentResponse(createdStudent));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable long id) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(new StudentResponse(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable long id, @RequestBody StudentRequest studentRequest) {
        Student studentToUpdate = StudentRequest.toStudent(studentRequest);
        studentToUpdate.setId(id);
        Student updatedStudent = studentService.update(studentToUpdate);
        return ResponseEntity.ok(new StudentResponse(updatedStudent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<Student> students = studentService.findAll();
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : students) {
            studentResponses.add(new StudentResponse(student));
        }
        return ResponseEntity.ok(studentResponses);
    }

    @GetMapping("/subject")
    public ResponseEntity<List<StudentResponse>> getStudentsBySubjectName(@RequestParam String subjectName) {
        List<Student> students = studentService.findBySubjectName(subjectName);
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : students) {
            studentResponses.add(new StudentResponse(student));
        }
        return ResponseEntity.ok(studentResponses);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<StudentResponse> getStudentByUsername(@PathVariable String username) {
        Student student = studentService.findByUsername(username);
        return ResponseEntity.ok(new StudentResponse(student));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<StudentResponse> getStudentByEmail(@PathVariable String email) {
        Student student = studentService.findByEmail(email);
        return ResponseEntity.ok(new StudentResponse(student));
    }
}
