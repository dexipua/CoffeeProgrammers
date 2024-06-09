package com.school.controller;

import com.school.dto.subject.SubjectRequest;
import com.school.dto.subject.SubjectResponseAll;
import com.school.dto.subject.SubjectResponseSimple;
import com.school.dto.subject.SubjectResponseWithTeacher;
import com.school.models.Subject;
import com.school.service.MarkService;
import com.school.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/subjects")
@RestController
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;
    private final MarkService markService;

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectResponseSimple create(
            @Valid @RequestBody SubjectRequest subjectRequest
    ) {
        return new SubjectResponseSimple(subjectService.create(subjectRequest));
    }


    @GetMapping("/getById/{subject_id}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponseAll getById(@PathVariable("subject_id") long subjectId) {
        return new SubjectResponseAll(subjectService.findById(subjectId));
    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    @PutMapping("/update/{subject_id}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponseAll update(
            @PathVariable("subject_id") long subjectId,
            @Valid @RequestBody SubjectRequest subjectRequest) {
        return new SubjectResponseAll(subjectService.update(subjectId, subjectRequest));


    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    @DeleteMapping("/delete/{subject_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("subject_id") long subjectId) {
        markService.deleteAllBySubjectId(subjectId);
        subjectService.delete(subjectId);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponseWithTeacher> getAllByOrderByName() {
        List<Subject> subjects = subjectService.getAllByOrderByName();
        return subjects.stream()
                .map(SubjectResponseWithTeacher::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllByName/")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponseSimple> getByNameContaining(
            @RequestParam("subject_name") String name) {
        List<Subject> subjects = subjectService.findByNameContaining(name);
        return subjects.stream()
                .map(SubjectResponseSimple::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllByTeacherId/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponseSimple> getByTeacherId(@PathVariable("teacher_id") long teacherId) {
        List<Subject> subjects = subjectService.findByTeacher_Id(teacherId);

        return subjects.stream()
                .map(SubjectResponseSimple::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    @PatchMapping("/update/{subject_id}/setTeacher/{teacher_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setTeacher(
            @PathVariable("subject_id") long subjectId,
            @PathVariable("teacher_id") long teacherId) {

        subjectService.setTeacher(subjectId, teacherId);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    @PatchMapping("/update/{subject_id}/deleteTeacher")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacher(@PathVariable("subject_id") long subjectId) {
        subjectService.deleteTeacher(subjectId);
    }


    @GetMapping("/getAllByStudentId/{student_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponseWithTeacher> getByStudentId(@PathVariable("student_id") long studentId) {
        List<Subject> subjects = subjectService.findByStudent_Id(studentId);

        return subjects.stream()
                .map(SubjectResponseWithTeacher::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or @userSecurity.checkUserBySubject(#auth, #subjectId)")
    @PatchMapping("/update/{subject_id}/addStudent/{student_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addStudent(
            @PathVariable("subject_id") long subjectId,
            @PathVariable("student_id") long studentId,
            Authentication auth) {

        subjectService.addStudent(subjectId, studentId);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or @userSecurity.checkUserBySubject(#auth, #subjectId)")
    @PatchMapping("/update/{subject_id}/deleteStudent/{student_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(
            @PathVariable("subject_id") long subjectId,
            @PathVariable("student_id") long studentId,
            Authentication auth) {
        subjectService.deleteStudent(subjectId, studentId);
    }
}