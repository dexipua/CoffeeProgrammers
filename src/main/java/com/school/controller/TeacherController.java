package com.school.controller;

import com.school.dto.teacher.TeacherResponseAll;
import com.school.dto.teacher.TeacherResponseSimple;
import com.school.dto.user.UserRequestCreate;
import com.school.dto.user.UserRequestUpdate;
import com.school.models.Teacher;
import com.school.service.StudentService;
import com.school.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final StudentService studentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    public TeacherResponseSimple create(
            @Valid @RequestBody UserRequestCreate userRequest) {
        return new TeacherResponseSimple(teacherService.create(userRequest));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or @userSecurity.checkUserByTeacher(#auth, #id)")
    public TeacherResponseAll update(
            @PathVariable long id,
            @Valid @RequestBody UserRequestUpdate userRequest,
            Authentication auth) {
        return new TeacherResponseAll(
                teacherService.update(id, userRequest),
                studentService.findStudentsByTeacherId(id));
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<TeacherResponseSimple> getAll() {
        List<Teacher> teachers = teacherService.findAll();
        return teachers.stream()
                .map(TeacherResponseSimple::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/getById/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherResponseAll getById(@PathVariable("teacher_id") long teacherId) {
        return new TeacherResponseAll(
                teacherService.findById(teacherId),
                studentService.findStudentsByTeacherId(teacherId));
    }

    @GetMapping("/getAllBySubjectName/")
    @ResponseStatus(HttpStatus.OK)
    public List<TeacherResponseSimple> getBySubjectName(
           @RequestParam("subject_name")  String subjectName) {
        List<Teacher> teachers = teacherService.findBySubjectName(subjectName);
        return teachers.stream()
                .map(TeacherResponseSimple::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    public void delete(@PathVariable("teacher_id") long teacher_id) {
        teacherService.delete(teacher_id);
    }

    @GetMapping("/getAllByName/")
    @ResponseStatus(HttpStatus.OK)
    public List<TeacherResponseSimple> getByName(
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName
    ) {
        List<Teacher> teachers = teacherService.findAllByUser_FirstNameAndAndUser_LastName(firstName, lastName);
        return teachers.stream()
                .map(TeacherResponseSimple::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/getByStudentId/{student_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<TeacherResponseSimple> getAllByStudentId(@PathVariable("student_id") long studentId) {
        List<Teacher> students = teacherService.findAllByStudentId(studentId);
        return students.stream()
                .map(TeacherResponseSimple::new)
                .collect(Collectors.toList());
    }
}
