package com.school.service.impl;

import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.repositories.SubjectRepository;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import com.school.service.TeacherService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Override
    public Subject create(@NotNull Subject subject) {
        try {
            findByName(subject.getName());
            throw new HttpClientErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Subject with name " + subject.getName() + " already exists"
            );
        } catch (IllegalArgumentException ignored) {
        }
        return subjectRepository.save(subject);
    }

    @Override
    public Subject findById(long id) {
        return subjectRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Subject with id " + id + " not found"));
    }

    @Override
    public Subject update(@NotNull Subject subject) {
        try {
            Subject check = findByName(subject.getName());
            if (check.getId() != subject.getId()) {
                throw new HttpClientErrorException(
                        HttpStatus.BAD_REQUEST,
                        "Subject with name " + subject.getName() + " already exists"
                );
            }
        } catch (IllegalArgumentException ignored) {
        }

        findById(subject.getId());
        return subjectRepository.save(subject);
    }

    @Override
    public void delete(long id) {
        Subject subject = findById(id);
        subject.setStudents(null);
        subjectRepository.delete(subject);
    }

    @Override
    public List<Subject> getAllByOrderByName() {
        Optional<List<Subject>> subjects = subjectRepository.findAllByOrderByName();
        return subjects.orElseGet(ArrayList::new);
    }

    @Override
    public Subject findByName(String name) {
        Optional<Subject> subject = subjectRepository.findByName(name);
        if (subject.isPresent()) {
            return subject.get();
        } else {
            throw new IllegalArgumentException("Subject not found");
        }
    }

    @Override
    public List<Subject> findByTeacher_Id(long teacherId) {
        teacherService.readById(teacherId);
        Optional<List<Subject>> subjects = subjectRepository.findByTeacher_Id(teacherId);
        if (subjects.isPresent()) {
            return subjects.get();
        } else {
            throw new IllegalArgumentException("Subjects with teacher id " + teacherId + " not found");
        }
    }

    @Override
    public void setTeacher(long subjectId, long teacherId) {
        Subject subject = findById(subjectId);
        Teacher teacher = teacherService.readById(teacherId);

        subject.setTeacher(teacher);

        subjectRepository.save(subject);
    }

    @Override
    public void deleteTeacher(long subjectId) {
        Subject subject = findById(subjectId);

        subject.setTeacher(null);

        subjectRepository.save(subject);
    }

    @Override
    public List<Subject> findByStudent_Id(long studentId) {
        studentService.findById(studentId);
        Optional<List<Subject>> subjects = subjectRepository.findByStudent_Id(studentId);
        if (subjects.isPresent()) {
            return subjects.get();
        } else {
            throw new IllegalArgumentException("Subjects with student id " + studentId + " not found");
        }
    }

    @Override
    public void addStudent(long subjectId, long studentId) {
        Subject subject = findById(subjectId);
        Student student = studentService.findById(studentId);

        if (subject.getStudents().contains(student)) {
            throw new HttpClientErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Subject already have this student"
            );
        }

        subject.getStudents().add(student);

        subjectRepository.save(subject);
    }

    @Override
    public void deleteStudent(long subjectId, long studentId) {
        Subject subject = findById(subjectId);
        Student student = studentService.findById(studentId);

        if (!subject.getStudents().contains(student)) {
            throw new HttpClientErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Subject doesn't have this student"
            );
        }

        subject.getStudents().remove(student);

        subjectRepository.save(subject);
    }
}
