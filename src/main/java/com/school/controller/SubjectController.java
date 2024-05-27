package com.school.controller;

import com.school.dto.SubjectRequest;
import com.school.dto.SubjectResponse;
import com.school.dto.TransformSubject;
import com.school.models.Subject;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import com.school.service.TeacherService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/subjects")
@RestController
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @PostMapping("/createSubject")
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectResponse create(@RequestBody SubjectRequest subjectRequest) {
        Subject subject = TransformSubject.
                transformFromRequestToModel(teacherService, studentService, subjectRequest);
        return new SubjectResponse(subjectService.create(subject));

    }


    @GetMapping("/findById/{subject_id}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponse getById(@PathVariable("subject_id") long subjectId) {
        return new SubjectResponse(subjectService.findById(subjectId));
    }

    @PutMapping("/updateSubject/{subject_id}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectResponse update(
            @PathVariable("subject_id") long subjectId,
            @RequestBody SubjectRequest subjectRequest) {

        Subject subjectToUpdate = TransformSubject.transformFromRequestToModel(teacherService, studentService, subjectRequest);
        subjectToUpdate.setId(subjectId);

        return new SubjectResponse(subjectService.update(subjectToUpdate));


    }

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

    @PatchMapping("/updateSubject/{subject_id}/setTeacher/{teacher_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setTeacher(
            @PathVariable("subject_id") long subjectId,
            @PathVariable("teacher_id") long teacherId) {

        subjectService.setTeacher(subjectId, teacherId);
    }

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

    @PatchMapping("/updateSubject/{subject_id}/addStudent/{student_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addStudent(
            @PathVariable("subject_id") long subjectId,
            @PathVariable("student_id") long studentId) {

        subjectService.addStudent(subjectId, studentId);
    }

    @PatchMapping("/updateSubject/{subject_id}/deleteStudent/{student_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(
            @PathVariable("subject_id") long subjectId,
            @PathVariable("student_id") long studentId) {
        subjectService.deleteStudent(subjectId, studentId);
    }
}
