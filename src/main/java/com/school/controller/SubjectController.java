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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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
    public ResponseEntity<SubjectResponse> create(@RequestBody SubjectRequest subjectRequest) {
        try {
            subjectService.findByName(subjectRequest.getName());
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Subject with name " + subjectRequest.getName() + " already exists");
        } catch (IllegalArgumentException ignored) {
        }

        Subject subject = TransformSubject.transformFromRequestToModel(teacherService, studentService, subjectRequest);
        SubjectResponse response = new SubjectResponse(subjectService.create(subject));
        return ResponseEntity.ok(response); // TODO -ok or created-
    }


    @GetMapping("/findById/{subject_id}")
    public ResponseEntity<SubjectResponse> getById(@PathVariable("subject_id") long subjectId) {
        try {
            return ResponseEntity.ok(new SubjectResponse(subjectService.findById(subjectId)));
        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/updateSubject/{subject_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SubjectResponse> update(@PathVariable("subject_id") long subjectId, @RequestBody SubjectRequest subjectRequest) {
        Subject subjectToUpdate = TransformSubject.transformFromRequestToModel(teacherService, studentService, subjectRequest);
        subjectToUpdate.setId(subjectId);

        //TODO

        try{
            return ResponseEntity.ok(new SubjectResponse(subjectService.update(subjectToUpdate)));
        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PatchMapping("/updateSubject/{subject_id}/setTeacher/{teacher_id}")
    public ResponseEntity<Void> setTeacher(@PathVariable("subject_id") long subjectId, @PathVariable("teacher_id") long teacherId) {
        Subject subject;
        try {
            subject = subjectService.findById(subjectId);
        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        Teacher teacher;
        try {
            teacher = teacherService.readById(teacherId);
        } catch (EntityNotFoundException e) { // TODO -IllegalArgumentException-
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        subject.setTeacher(teacher);

        subjectService.update(subject);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateSubject/{subject_id}/deleteTeacher")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("subject_id") long subjectId) {
        Subject subject;
        try {
            subject = subjectService.findById(subjectId);
        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        subject.setTeacher(null);

        subjectService.update(subject);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateSubject/{subject_id}/addStudent/{student_id}")
    public ResponseEntity<Void> addStudent(@PathVariable("subject_id") long subjectId, @PathVariable("student_id") long studentId) {
        Subject subject;
        try {
            subject = subjectService.findById(subjectId);
        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        Student student;
        try {
            student = studentService.findById(studentId);
        } catch (EntityNotFoundException e) { // TODO -IllegalArgumentException-
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        if(subject.getStudents().contains(student)){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Subject already have this student");
        }
        subject.getStudents().add(student);

        subjectService.update(subject);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateSubject/{subject_id}/deleteStudent/{student_id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("subject_id") long subjectId, @PathVariable("student_id") long studentId) {
        Subject subject;
        try {
            subject = subjectService.findById(subjectId);
        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        Student student;
        try {
            student = studentService.findById(studentId);
        } catch (EntityNotFoundException e) { // TODO -IllegalArgumentException-
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        if(!subject.getStudents().contains(student)){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Subject doesn't have this student");
        }

        subject.getStudents().remove(student);

        subjectService.update(subject);

        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/deleteSubject/{subject_id}")
    public ResponseEntity<Void> delete(@PathVariable("subject_id") long subjectId) {
        try {
            subjectService.delete(subjectId);
        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.noContent().build(); //TODO -check this-

    }

    @GetMapping("/findAll")
    public ResponseEntity<List<SubjectResponse>> getAllByOrderByName() { // TODO
        List<Subject> subjects = subjectService.getAllByOrderByName();
        List<SubjectResponse> subjectResponses = subjects.stream().
                map(SubjectResponse::new).
                collect(Collectors.toList());
        return ResponseEntity.ok(subjectResponses);
    }

    @GetMapping("/findByName/{subject_name}")
    public ResponseEntity<?> getByName(@PathVariable("subject_name") String name) {
        try {
            return ResponseEntity.ok(new SubjectResponse(subjectService.findByName(name)));
        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/findByTeacherId/{teacher_id}")
    public ResponseEntity<?> getByTeacherId(@PathVariable("teacher_id") long teacherId) {
        List<Subject> subjects;
        try {
            subjects = subjectService.findByTeacher_Id(teacherId);

        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        List<SubjectResponse> subjectResponses = subjects.stream().
                map(SubjectResponse::new).
                collect(Collectors.toList());
        return ResponseEntity.ok(subjectResponses);
    }

    @GetMapping("/findByStudentId/{student_id}")
    public ResponseEntity<?> getByStudentId(@PathVariable("student_id") long studentId) {
        List<Subject> subjects;
        try {
            subjects = subjectService.findByStudent_Id(studentId);
        } catch (IllegalArgumentException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        List<SubjectResponse> subjectResponses = subjects.stream().
                map(SubjectResponse::new).
                collect(Collectors.toList());
        return ResponseEntity.ok(subjectResponses);
    }
}
