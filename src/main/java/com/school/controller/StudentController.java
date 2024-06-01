package com.school.controller;

import com.school.dto.student.StudentRequest;
import com.school.dto.student.StudentResponseAll;
import com.school.dto.student.StudentResponseToGet;
import com.school.models.Student;
import com.school.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        return new StudentResponseAll(studentService.findById(id));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or @userSecurity.checkUserByStudent(#auth, #id)")
    public StudentResponseAll updateStudent(@PathVariable long id, @RequestBody StudentRequest studentRequest, Authentication auth) {
        Student updateStudent = StudentRequest.toStudent(studentRequest);
        updateStudent.setId(id);
        return new StudentResponseAll(studentService.update(updateStudent));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    public void deleteStudent(@PathVariable long id) {
        studentService.deleteById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseToGet> getAllStudents() {
        List<Student> students = studentService.findAllOrderedByName();
        return students.stream()
                .map(StudentResponseToGet::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/byEmail/{email}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponseToGet getByEmail(@PathVariable("email") String email){
        return new StudentResponseToGet(studentService.findByEmail(email));
    }

    @GetMapping("/bySubjectName/{subject_name}")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseToGet> getStudentsBySubjectName(@PathVariable("subject_name") String subjectName) {
        List<Student> students = studentService.findBySubjectName(subjectName);
        return students.stream()
                .map(StudentResponseToGet::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/byTeacherId/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseToGet> getStudentsByTeacherId( @PathVariable("teacher_id") long teacherId) {
        List<Student> students = studentService.findStudentsByTeacherId(teacherId);
        return students.stream()
                .map(StudentResponseToGet::new)
                .collect(Collectors.toList());
    }
}
