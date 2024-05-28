package com.school.controller;

import com.school.dto.StudentRequest;
import com.school.dto.StudentResponseAll;
import com.school.dto.StudentResponseToGet;
import com.school.models.Student;
import com.school.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    public StudentResponseAll createStudent(@RequestBody StudentRequest studentRequest) {
        Student student = StudentRequest.toStudent(studentRequest);
        Student createdStudent = studentService.create(student);
        return new StudentResponseAll(createdStudent);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponseAll getStudentById(@PathVariable long id) {
        Student student;
        student = studentService.findById(id);
        return new StudentResponseAll(student);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or @userSecurity.checkUserByStudent(#auth, #id)")
    public StudentResponseAll updateStudent(@PathVariable long id, @RequestBody StudentRequest studentRequest, Authentication auth) {
        Student studentToUpdate = StudentRequest.toStudent(studentRequest);
        studentToUpdate.setId(id);
        Student updatedStudent = studentService.update(studentToUpdate);
        return new StudentResponseAll(updatedStudent);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<StudentResponseToGet>> getAllStudents() {
        List<Student> students = studentService.findAll();
        List<StudentResponseToGet> studentResponses = new ArrayList<>();
        for (Student student : students) {
            studentResponses.add(new StudentResponseToGet(student));
        }
        return ResponseEntity.ok(studentResponses);
    }

    @GetMapping("/by/{subject_name}")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseToGet> getStudentsBySubjectName(@PathVariable("subject_name") String subjectName) {
        List<Student> students;
        students = studentService.findBySubjectName(subjectName);
        List<StudentResponseToGet> studentResponses = new ArrayList<>();
        for (Student student : students) {
            studentResponses.add(new StudentResponseToGet(student));
        }
        return studentResponses;
    }

    @GetMapping("/by/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseToGet> getStudentsByTeacherId( @PathVariable("teacher_id") long teacherId) {
        List<Student> students;
        students = studentService.findStudentsByTeacherId(teacherId);
        List<StudentResponseToGet> studentResponses = new ArrayList<>();
        for (Student student : students) {
            studentResponses.add(new StudentResponseToGet(student));
        }
        return studentResponses;
    }
}
