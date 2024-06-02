package com.school.controller;

import com.school.dto.mark.*;
import com.school.models.Mark;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.service.impl.MarkServiceImpl;
import com.school.service.impl.StudentServiceImpl;
import com.school.service.impl.SubjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marks")
public class MarkController {
    private final MarkServiceImpl markService;
    private final StudentServiceImpl studentService;
    private final SubjectServiceImpl subjectService;

    @PostMapping("/{subject_id}/{student_id}/createMark")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_CHIEF_TEACHER')") // TODO PreAuthorize
    public MarkResponseAll createStudent(
            @RequestBody MarkRequest markRequest,
            @PathVariable("subject_id") long subject_id,
            @PathVariable("student_id") long student_id) {
        Mark mark = MarkRequest.toMark(markRequest);
        mark.setSubject(subjectService.findById(subject_id));
        mark.setStudent(studentService.findById(student_id));
        mark.setTime(LocalDateTime.now());
        return new MarkResponseAll(markService.create(mark));
    }

    @GetMapping("/getById/{id}")
    @ResponseStatus(HttpStatus.OK)
    // TODO PreAutorize
    public MarkResponseAll getByMarkId(
            @PathVariable long id) {
        return new MarkResponseAll(markService.findById(id));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_CHIEF_TEACHER')") // TODO PreAuthorize
    public MarkResponseAll updateMark(
            @PathVariable long id,
            @RequestBody MarkRequest markRequest) {
        Mark updateMark = MarkRequest.toMark(markRequest);
        updateMark.setId(id);
        updateMark.setTime(LocalDateTime.now());
        return new MarkResponseAll(markService.update(updateMark));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_CHIEF_TEACHER')") // TODO PreAutorize
    public void deleteMark(
            @PathVariable long id) {
        markService.delete(id);
    }

    @GetMapping("/getAllByStudentId/{student_id}")
    @ResponseStatus(HttpStatus.OK)
    // TODO PreAutorize
    public List<MarkResponseToGetByStudent> getAllByStudentId(
            @PathVariable long student_id) {
        HashMap<Subject, List<Mark>> marks = markService.findAllByStudentId(student_id);
        List<MarkResponseToGetByStudent> result = new ArrayList<>();
        for(Map.Entry<Subject, List<Mark>> entry : marks.entrySet()) {
            result.add(new MarkResponseToGetByStudent(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    @GetMapping("/getAllBySubjectId/{subject_id}")
    @ResponseStatus(HttpStatus.OK)
    // TODO PreAutorize
    public List<MarkResponseToGetBySubject> getAllBySubjectId(
            @PathVariable long subject_id) {
        HashMap<Student, List<Mark>> marks = markService.findAllBySubjectId(subject_id);
        List<MarkResponseToGetBySubject> result = new ArrayList<>();
        for(Map.Entry<Student, List<Mark>> entry : marks.entrySet()) {
            result.add(new MarkResponseToGetBySubject(entry.getKey(), entry.getValue()));
        }
        return result;
    }
}
