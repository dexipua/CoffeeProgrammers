package com.school.controller;

import com.school.dto.mark.MarkRequest;
import com.school.dto.mark.MarkResponseAll;
import com.school.dto.mark.MarkResponseForStudent;
import com.school.dto.mark.MarkResponseForSubject;
import com.school.models.Mark;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.service.impl.MarkServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marks")
public class MarkController {
    private final MarkServiceImpl markService;

    @PostMapping("/subject/{subject_id}/student/{student_id}/createMark")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@userSecurity.checkUserBySubject(#auth, #subjectId)")
    public MarkResponseAll createMark(
            @Valid @RequestBody MarkRequest markRequest,
            @PathVariable("subject_id") long subjectId,
            @PathVariable("student_id") long studentId,
            Authentication auth
    ) {
        return new MarkResponseAll(markService.create(markRequest, subjectId, studentId));
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(
            "@userSecurity.checkUserByMarkByStudent(#auth, #id) or " +
                    "@userSecurity.checkUserByMarkByTeacher(#auth, #id)")
    public MarkResponseAll getByMarkId(
            @PathVariable long id,
            Authentication auth) {
        return new MarkResponseAll(markService.findById(id));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@userSecurity.checkUserByMarkByTeacher(#auth, #id)")
    public MarkResponseAll updateMark(
            @PathVariable long id,
            @Valid @RequestBody MarkRequest markRequest,
            Authentication auth
    ) {
        return new MarkResponseAll(markService.update(id, markRequest));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@userSecurity.checkUserByMarkByTeacher(#auth, #id)")
    public void deleteMark(
            @PathVariable long id,
            Authentication auth
    ) {
        markService.delete(id);
    }

    @GetMapping("/getAllByStudentId/{student_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@userSecurity.checkUserByStudent(#auth, #student_id)")
    public List<MarkResponseForStudent> getAllByStudentId(
            @PathVariable long student_id, Authentication auth
    ) {
        HashMap<Subject, List<Mark>> marks = markService.findAllByStudentId(student_id);
        return marks.entrySet().stream()
                .map(entry -> new MarkResponseForStudent(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllBySubjectId/{subject_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@userSecurity.checkUserBySubject(#auth, #subject_id)")
    public List<MarkResponseForSubject> getAllBySubjectId(
            @PathVariable long subject_id, Authentication auth
    ) {
        HashMap<Student, List<Mark>> marks = markService.findAllBySubjectId(subject_id);
        return marks.entrySet().stream()
                .map(entry -> new MarkResponseForSubject(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
