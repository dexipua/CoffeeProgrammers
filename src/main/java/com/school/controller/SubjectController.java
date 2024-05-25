package com.school.controller;

import com.school.dto.SubjectRequest;
import com.school.dto.SubjectResponse;
import com.school.dto.TransformSubject;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import com.school.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> create(@RequestBody SubjectRequest subjectRequest) {
        try {
            subjectService.findByName(subjectRequest.getName());
            return new ResponseEntity<>(
                    "Subject with name " + subjectRequest.getName() + " already exists"
                    , HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException ignored) {
        }

        Subject subject = TransformSubject.transformFromRequestToModel(teacherService, studentService, subjectRequest);
        SubjectResponse response = new SubjectResponse(subjectService.create(subject));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/findById/{subject_id}")
    public ResponseEntity<?> getById(
            @PathVariable("subject_id") long subjectId) {
        try {
            return ResponseEntity.ok(new SubjectResponse(subjectService.findById(subjectId)));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateSubject/{subject_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(
            @PathVariable("subject_id") long subjectId,
            @RequestBody SubjectRequest subjectRequest) {

        try {
            subjectService.findById(subjectId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        try {
            Subject subject = subjectService.findByName(subjectRequest.getName());
            if (subject.getId() != subjectId) {
                return new ResponseEntity<>(
                        "Subject with name " + subjectRequest.getName() + " already exists",
                        HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException ignored) {
        }

        Subject subjectToUpdate = TransformSubject.transformFromRequestToModel(teacherService, studentService, subjectRequest);
        subjectToUpdate.setId(subjectId);

        return ResponseEntity.ok(new SubjectResponse(subjectService.update(subjectToUpdate)));


    }

    @PatchMapping("/updateSubject/{subject_id}/setTeacher/{teacher_id}")
    public ResponseEntity<?> setTeacher(
            @PathVariable("subject_id") long subjectId,
            @PathVariable("teacher_id") long teacherId
    ) {

        Subject subject;
        Teacher teacher;
        try {
            subject = subjectService.findById(subjectId);
            teacher = teacherService.readById(teacherId); //TODO -findById-
        } catch (IllegalArgumentException | EntityNotFoundException e) { // TODO -only IllegalArgumentException-
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }


//        if (subject.getTeacher() != null && subject.getTeacher().getId() == teacherId) { //TODO -check-
//            return ResponseEntity.ok().build();
//        }


        subject.setTeacher(teacher);
        subjectService.update(subject);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateSubject/{subject_id}/deleteTeacher")
    public ResponseEntity<?> deleteTeacher(
            @PathVariable("subject_id") long subjectId) {
        Subject subject;
        try {
            subject = subjectService.findById(subjectId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

//        if (subject.getTeacher() == null) { //TODO -check-
//            return ResponseEntity.ok().build();
//        }

        subject.setTeacher(null);
        subjectService.update(subject);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateSubject/{subject_id}/addStudent/{student_id}")
    public ResponseEntity<?> addStudent(
            @PathVariable("subject_id") long subjectId,
            @PathVariable("student_id") long studentId) {
        Subject subject;
        Student student;
        try {
            subject = subjectService.findById(subjectId);
            student = studentService.findById(studentId);
        } catch (IllegalArgumentException | EntityNotFoundException e) { // TODO -only IllegalArgumentException-
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        if (subject.getStudents().contains(student)) {
            return new ResponseEntity<>(
                    "Subject already have this student",
                    HttpStatus.BAD_REQUEST);
        }

        subject.getStudents().add(student);
        subjectService.update(subject);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateSubject/{subject_id}/deleteStudent/{student_id}")
    public ResponseEntity<?> deleteStudent(
            @PathVariable("subject_id") long subjectId,
            @PathVariable("student_id") long studentId) {
        Subject subject;
        Student student;
        try {
            subject = subjectService.findById(subjectId);
            student = studentService.findById(studentId);
        } catch (IllegalArgumentException | EntityNotFoundException e) { // TODO -only IllegalArgumentException-
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        if (!subject.getStudents().contains(student)) {
            return new ResponseEntity<>(
                    "Subject doesn't have this student",
                    HttpStatus.BAD_REQUEST);
        }

        subject.getStudents().remove(student);
        subjectService.update(subject);

        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/deleteSubject/{subject_id}")
    public ResponseEntity<?> delete(
            @PathVariable("subject_id") long subjectId) {
        try {
            subjectService.findById(subjectId).setStudents(null);
            subjectService.delete(subjectId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/findAll")
    public ResponseEntity<List<SubjectResponse>> getAllByOrderByName() {
        List<Subject> subjects = subjectService.getAllByOrderByName();
        List<SubjectResponse> subjectResponses = subjects.stream()
                .map(SubjectResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(subjectResponses);
    }

    @GetMapping("/findByName/{subject_name}")
    public ResponseEntity<?> getByName(
            @NotNull @PathVariable("subject_name") String name) {
        try {
            return ResponseEntity.ok(new SubjectResponse(subjectService.findByName(name)));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByTeacherId/{teacher_id}")
    public ResponseEntity<?> getByTeacherId(
            @PathVariable("teacher_id") long teacherId) {
        List<Subject> subjects;
        try {
            teacherService.readById(teacherId);
            subjects = subjectService.findByTeacher_Id(teacherId);
        } catch (IllegalArgumentException | EntityNotFoundException e) { // TODO -only IllegalArgumentException-
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
        List<SubjectResponse> subjectResponses = subjects.stream()
                .map(SubjectResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(subjectResponses);
    }

    @GetMapping("/findByStudentId/{student_id}")
    public ResponseEntity<?> getByStudentId(
            @PathVariable("student_id") long studentId) {

        List<Subject> subjects;
        try {
            studentService.findById(studentId);
            subjects = subjectService.findByStudent_Id(studentId);
        } catch (IllegalArgumentException | EntityNotFoundException e) { // TODO -only IllegalArgumentException-
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        List<SubjectResponse> subjectResponses = subjects.stream()
                .map(SubjectResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(subjectResponses);
    }
}
