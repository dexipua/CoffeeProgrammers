package com.school.service.impl;

import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.repositories.SubjectRepository;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import com.school.service.TeacherService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Override
    public Subject create(@NotNull Subject subject) {
        if (subjectRepository.findByName(subject.getName()).isPresent()){
            throw new EntityExistsException(
                    "Subject with name " + subject.getName() + " already exists"
            );
        }
        return subjectRepository.save(subject);
    }

    @Override
    public Subject findById(long id) {
        return subjectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Subject with id " + id + " not found"));
    }

    @Override
    public Subject update(@NotNull Subject updatedSubject) {
        Subject actualSubject = findById(updatedSubject.getId());

        String updatedName = updatedSubject.getName();
        String actualName = actualSubject.getName();

        if (!updatedName.equals(actualName) &&
                subjectRepository.findByName(updatedName).isPresent()){
            throw new EntityExistsException(
                    "Subject with name " + updatedName + " already exists"
            );
        }

        updatedSubject.setTeacher(actualSubject.getTeacher());
        updatedSubject.setStudents(actualSubject.getStudents());

        return subjectRepository.save(updatedSubject);
    }

    @Override
    public void delete(long id) {
        Subject subject = findById(id);
        subject.setStudents(null);
        subjectRepository.delete(subject);
    }

    @Override
    public List<Subject> getAllByOrderByName() {
        return subjectRepository.findAllByOrderByName();
    }

    @Override
    public List<Subject> findByNameContaining(String name) {
        return subjectRepository.findByNameContaining(name);
    }

    @Override
    public List<Subject> findByTeacher_Id(long teacherId) {
        teacherService.findById(teacherId);
        return subjectRepository.findByTeacher_Id(teacherId);
    }

    @Override
    public void setTeacher(long subjectId, long teacherId) {
        Subject subject = findById(subjectId);
        Teacher teacher = teacherService.findById(teacherId);

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
        return subjectRepository.findByStudent_Id(studentId);
    }

    @Override
    public void addStudent(long subjectId, long studentId) {
        Subject subject = findById(subjectId);
        Student student = studentService.findById(studentId);

        if (subject.getStudents().contains(student)) {
            throw new UnsupportedOperationException(
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
            throw new UnsupportedOperationException(
                    "Subject doesn't have this student"
            );
        }

        subject.getStudents().remove(student);

        subjectRepository.save(subject);
    }
}
