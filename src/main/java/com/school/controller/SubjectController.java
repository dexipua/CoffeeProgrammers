package com.school.controller;

import com.school.dto.subject.SubjectRequest;
import com.school.dto.subject.SubjectResponse;
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
    @PostMapping("/createSubject")
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectResponse create(@RequestBody SubjectRequest subjectRequest) {
        Subject subject = TransformSubject.
                transformFromRequestToModel(subjectRequest);
        return new SubjectResponse(subjectService.create(subject));
    }


    @GetMapping("/findById/{subject_id}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponse getById(@PathVariable("subject_id") long subjectId) {
        return new SubjectResponse(subjectService.findById(subjectId));
    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    @PutMapping("/updateSubject/{subject_id}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponse update( //TODO
            @PathVariable("subject_id") long subjectId,
            @RequestBody SubjectRequest subjectRequest) {

        Subject subject = subjectService.findById(subjectId);
        Subject subjectToUpdate = TransformSubject.transformFromRequestToModel(subjectRequest);
        subjectToUpdate.setId(subjectId);
        subjectToUpdate.setTeacher(subject.getTeacher());
        subjectToUpdate.setStudents(subject.getStudents());

        return new SubjectResponse(subjectService.update(subjectToUpdate));


    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    @DeleteMapping("/deleteSubject/{subject_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("subject_id") long subjectId) {
        subjectService.delete(subjectId);
    }

    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponse> getAllByOrderByName() {
        List<Subject> subjects = subjectService.getAllByOrderByName();
        return subjects.stream()
                .map(SubjectResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/findByName/{subject_name}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponse getByName(@NotNull @PathVariable("subject_name") String name) {
        return new SubjectResponse(subjectService.findByName(name));
    }

    @GetMapping("/findByTeacherId/{teacher_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponse> getByTeacherId(@PathVariable("teacher_id") long teacherId) {
        List<Subject> subjects = subjectService.findByTeacher_Id(teacherId);

        return subjects.stream()
                .map(SubjectResponse::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    @PatchMapping("/updateSubject/{subject_id}/setTeacher/{teacher_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setTeacher(
            @PathVariable("subject_id") long subjectId,
            @PathVariable("teacher_id") long teacherId) {

        subjectService.setTeacher(subjectId, teacherId);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    @PatchMapping("/updateSubject/{subject_id}/deleteTeacher")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacher(@PathVariable("subject_id") long subjectId) {
        subjectService.deleteTeacher(subjectId);
    }



    @GetMapping("/findByStudentId/{student_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectResponse> getByStudentId(@PathVariable("student_id") long studentId) {
        List<Subject> subjects = subjectService.findByStudent_Id(studentId);

        return subjects.stream()
                .map(SubjectResponse::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or @userSecurity.checkUserBySubject(#auth, #subjectId)")
    @PatchMapping("/updateSubject/{subject_id}/addStudent/{student_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addStudent(
            @PathVariable("subject_id") long subjectId,
            @PathVariable("student_id") long studentId,
            Authentication auth) {

        subjectService.addStudent(subjectId, studentId);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER') or @userSecurity.checkUserBySubject(#auth, #subjectId)")
    @PatchMapping("/updateSubject/{subject_id}/deleteStudent/{student_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(
            @PathVariable("subject_id") long subjectId,
            @PathVariable("student_id") long studentId,
            Authentication auth) {
        subjectService.deleteStudent(subjectId, studentId);
    }
}
