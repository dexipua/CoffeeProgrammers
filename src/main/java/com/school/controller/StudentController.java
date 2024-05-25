package com.school.controller;

import com.school.dto.StudentRequest;
import com.school.dto.StudentResponse;
import com.school.models.Student;
import com.school.repositories.RoleRepository;
import com.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final RoleRepository roleRepository;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    public ResponseEntity<?> createStudent(@RequestBody StudentRequest studentRequest) {
        try{
            studentService.findByUsername(studentRequest.getUsername());
            studentService.findByEmail(studentRequest.getEmail());
            return new ResponseEntity<>("Such student with this username or email is exist", HttpStatus.BAD_REQUEST);
        }catch (IllegalArgumentException e){}
        Student student = StudentRequest.toStudent(studentRequest);
        student.getUser().setRole(roleRepository.findByName("STUDENT").get());
        Student createdStudent = studentService.create(student);
        return ResponseEntity.ok(new StudentResponse(createdStudent));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable long id) {
        Student student;
        try {
            student = studentService.findById(id);
        }catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new StudentResponse(student));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or @userSecurity.checkUserId(#auth,#id)")
    public ResponseEntity<?> updateStudent(@PathVariable long id, @RequestBody StudentRequest studentRequest) {
        try{
            Student student = studentService.findByUsername(studentRequest.getUsername());
            if(student.getId() != id){
                return new ResponseEntity<>("Student with this username is exist", HttpStatus.BAD_REQUEST);
            }
            student = studentService.findByEmail(studentRequest.getEmail());
            if (student.getId() != id){
                return new ResponseEntity<>("Student with this email is exist", HttpStatus.BAD_REQUEST);
            }
        }catch (IllegalArgumentException e){}
        Student studentToUpdate = StudentRequest.toStudent(studentRequest);
        studentToUpdate.setId(id);
        studentToUpdate.getUser().setRole(roleRepository.findByName("STUDENT").get());
        Student updatedStudent = studentService.update(studentToUpdate);
        return ResponseEntity.ok(new StudentResponse(updatedStudent));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
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
    public ResponseEntity<?> getStudentsBySubjectName(@PathVariable("subject_name") String subjectName) {
        List<Student> students;
        try{
            students = studentService.findBySubjectName(subjectName);
        }catch (IllegalArgumentException ex){
            return new ResponseEntity<>("Student with this email is exist", HttpStatus.BAD_REQUEST);
        }
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : students) {
            studentResponses.add(new StudentResponse(student));
        }
        return ResponseEntity.ok(studentResponses);
    }

    @GetMapping("/by/username/{username}")
    public ResponseEntity<?> getStudentByUsername(@PathVariable String username) {
        Student student;
        try{
            student = studentService.findByUsername(username);
        }catch (IllegalArgumentException ex){
            return new ResponseEntity<>("Student with this email is exist", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new StudentResponse(student));
    }
}
