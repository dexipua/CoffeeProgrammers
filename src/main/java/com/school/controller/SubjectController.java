package com.school.controller;

import com.school.dto.subject.SubjectRequest;
import com.school.dto.subject.SubjectResponseAll;
import com.school.dto.subject.SubjectResponseToGet;
import com.school.dto.subject.TransformSubject;
import com.school.models.Subject;
import com.school.service.SubjectService;
import jakarta.validation.constraints.NotNull;
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

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectResponseToGet create(@RequestBody SubjectRequest subjectRequest) {
        Subject subject = TransformSubject.
                transformFromRequestToModel(subjectRequest);
        return new SubjectResponseToGet(subjectService.create(subject));
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
            @RequestBody SubjectRequest subjectRequest) {

        Subject subjectToUpdate = TransformSubject.transformFromRequestToModel(subjectRequest);
        subjectToUpdate.setId(subjectId);

        return new SubjectResponseAll(subjectService.update(subjectToUpdate));


    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    @DeleteMapping("/delete/{subject_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("subject_id") long subjectId) {
        subjectService.delete(subjectId);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponseToGet> getAllByOrderByName() {
        List<Subject> subjects = subjectService.getAllByOrderByName();
        return subjects.stream()
                .map(SubjectResponseToGet::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllByName/")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponseToGet> getByNameContaining(@NotNull @RequestParam("subject_name") String name) {
       List<Subject> subjects = subjectService.findByNameContaining(name);
        return subjects.stream()
                .map(SubjectResponseToGet::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllByTeacherId/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponseToGet> getByTeacherId(@PathVariable("teacher_id") long teacherId) {
        List<Subject> subjects = subjectService.findByTeacher_Id(teacherId);

        return subjects.stream()
                .map(SubjectResponseToGet::new)
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
    public List<SubjectResponseToGet> getByStudentId(@PathVariable("student_id") long studentId) {
        List<Subject> subjects = subjectService.findByStudent_Id(studentId);

        return subjects.stream()
                .map(SubjectResponseToGet::new)
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
