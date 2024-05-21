package com.school.controller;

import com.school.dto.StudentRequest;
import com.school.dto.StudentResponse;
import com.school.models.Student;
import com.school.repositories.RoleRepository;
import com.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/get/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable long id) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(new StudentResponse(student));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable long id, @RequestBody StudentRequest studentRequest) {
        Student studentToUpdate = StudentRequest.toStudent(studentRequest);
        studentToUpdate.setId(id);
        Student updatedStudent = studentService.update(studentToUpdate);
        return ResponseEntity.ok(new StudentResponse(updatedStudent));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<Student> students = studentService.findAll();
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : students) {
            studentResponses.add(new StudentResponse(student));
        }
        return ResponseEntity.ok(studentResponses);
    }

    @GetMapping("/by/{subject_name}")
    public ResponseEntity<List<StudentResponse>> getStudentsBySubjectName(@PathVariable("subject_name") String subjectName) {
        List<Student> students = studentService.findBySubjectName(subjectName);
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : students) {
            studentResponses.add(new StudentResponse(student));
        }
        return ResponseEntity.ok(studentResponses);
    }

    @GetMapping("/by/username/{username}")
    public ResponseEntity<StudentResponse> getStudentByUsername(@PathVariable String username) {
        Student student = studentService.findByUsername(username);
        return ResponseEntity.ok(new StudentResponse(student));
    }

    @GetMapping("/by/email/{email}")
    public ResponseEntity<StudentResponse> getStudentByEmail(@PathVariable String email) {
        Student student = studentService.findByEmail(email);
        StudentResponse response = new StudentResponse(student);
        return ResponseEntity.ok(response);
    }
}
