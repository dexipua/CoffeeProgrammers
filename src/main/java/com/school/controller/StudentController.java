package com.school.controller;

import com.school.dto.student.StudentResponseAll;
import com.school.dto.student.StudentResponseSimple;
import com.school.dto.student.StudentResponseWithEmail;
import com.school.dto.user.UserRequestCreate;
import com.school.dto.user.UserRequestUpdate;
import com.school.models.Student;
import com.school.service.MarkService;
import com.school.service.StudentService;
import com.school.service.UserNewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final MarkService markService;
    private final UserNewsService userNewsService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or hasRole('ROLE_TEACHER')")
    public StudentResponseSimple create(
            @Valid @RequestBody UserRequestCreate userRequest) {
        return new StudentResponseSimple(studentService.create(userRequest));
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponseAll getById(@PathVariable long id) {
        return new StudentResponseAll(
                studentService.findById(id),
                markService.findAverageByStudentId(id)
        );
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or @userSecurity.checkUserByStudent(#auth, #id)")
    public StudentResponseAll update(
            @PathVariable long id,
            @Valid @RequestBody UserRequestUpdate userRequest,
            Authentication auth
    ) {
        return new StudentResponseAll(
                studentService.update(id, userRequest),
                markService.findAverageByStudentId(id)
        );
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or hasRole('ROLE_TEACHER')")
    public void delete(@PathVariable long id) {
        markService.deleteAllByStudentId(id);
        userNewsService.deleteAllByUserId(studentService.findById(id).getUser().getId());
        studentService.deleteById(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseWithEmail> getAll() {
        List<Student> students = studentService.findAllOrderedByName();
        return students.stream()
                .map(StudentResponseWithEmail::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllByTeacherId/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseWithEmail> getByTeacherId(@PathVariable("teacher_id") long teacherId) {
        List<Student> students = studentService.findStudentsByTeacherId(teacherId);
        return students.stream()
                .map(StudentResponseWithEmail   ::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllByName/")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseWithEmail> getByName(
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName
    ) {
        List<Student> students = studentService.findAllByUser_FirstNameAndUser_LastName(firstName, lastName);
        return students.stream()
                .map(StudentResponseWithEmail::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> getStudentsCount() {
        return ResponseEntity.ok(studentService.getStudentsCount());
    }

    @GetMapping("/subjectIdIsNot/{subject_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponseWithEmail> findAllBySubjectsIdIsNot(
            @PathVariable("subject_id") long subjectId){
        return studentService.findAllBySubjectsIdIsNot(subjectId).stream()
                .map(StudentResponseWithEmail::new).
                collect(Collectors.toList());
    }

}
