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
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or hasRole('ROLE_TEACHER')")
    public StudentResponseToGet create(@RequestBody StudentRequest studentRequest) {
        Student student = StudentRequest.toStudent(studentRequest);
        Student createdStudent = studentService.create(student);
        return new StudentResponseToGet(createdStudent);
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponseAll getById(@PathVariable long id) {
        return new StudentResponseAll(studentService.findById(id));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or @userSecurity.checkUserByStudent(#auth, #id)")
    public StudentResponseAll update(@PathVariable long id, @RequestBody StudentRequest studentRequest, Authentication auth) {
        Student updateStudent = StudentRequest.toStudent(studentRequest);
        updateStudent.setId(id);
        return new StudentResponseAll(studentService.update(updateStudent));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or hasRole('ROLE_TEACHER')")
    public void delete(@PathVariable long id) {
        studentService.deleteById(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseToGet> getAll() {
        List<Student> students = studentService.findAllOrderedByName();
        return students.stream()
                .map(StudentResponseToGet::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllBySubjectName/")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseToGet> getBySubjectName(
            @RequestParam("subject_name") String subjectName) {

        List<Student> students = studentService.findBySubjectName(subjectName);
        return students.stream()
                .map(StudentResponseToGet::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllByTeacherId/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseToGet> getByTeacherId( @PathVariable("teacher_id") long teacherId) {
        List<Student> students = studentService.findStudentsByTeacherId(teacherId);
        return students.stream()
                .map(StudentResponseToGet::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllByName/")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseToGet> getByName( @RequestParam String firstName, @RequestParam String lastName) {
        List<Student> students = studentService.findAllByUser_FirstNameAndAndUser_LastName(firstName, lastName);
        return students.stream()
                .map(StudentResponseToGet::new)
                .collect(Collectors.toList());
    }
}
