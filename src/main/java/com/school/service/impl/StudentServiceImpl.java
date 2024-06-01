package com.school.service.impl;


import com.school.models.Student;
import com.school.models.Teacher;
import com.school.repositories.StudentRepository;
import com.school.service.RoleService;
import com.school.service.StudentService;
import com.school.service.TeacherService;
import com.school.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UserService userService;
    private final TeacherService teacherService;
    private final StudentRepository studentRepository;
    private final RoleService roleService;

    @Override
    public Student create(@NotNull Student student) {
        student.getUser().setRole(roleService.findByName("STUDENT"));
        userService.create(student.getUser());
        return studentRepository.save(student);
    }

    @Override
    public Student findById(long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Student with id " + id + " not found"));
    }

    @Override
    public Student update(@NotNull Student student) {
        Student oldStudent = findById(student.getId());
        student.getUser().setEmail(oldStudent.getUser().getEmail());
        student.getUser().setRole(oldStudent.getUser().getRole());
        student.getUser().setId(oldStudent.getUser().getId());
        student.setSubjects(oldStudent.getSubjects());
        userService.update(student.getUser());
        return student;
    }

    @Override
    public void deleteById(long id) {
        Student student = findById(id);
        studentRepository.delete(student);
    }

    @Override
    public List<Student> findAllOrderedByName() {
        return studentRepository.findAllByOrderByUser();
    }

    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByUserEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Student with " + email + " not found"));
    }

    @Override
    public List<Student> findBySubjectName(String subjectName) {
        return studentRepository.findStudentBySubjectName(subjectName);
    }

    // TODO -------------------------------------------------------------------
    // TODO -to-repository-
    @Override
    public List<Student> findStudentsByTeacherId(long teacherId) {
        HashSet<Student> students = new HashSet<>();
        teacherService.findById(teacherId).getSubjects()
                .forEach(subject -> students.addAll(subject.getStudents()));
        return new ArrayList<>(students);
    }

    @Override
    public List<Student> findAllByUser_FirstNameAndAndUser_LastName(String firstName, String lastName) {
        return studentRepository.findAllByUser_FirstNameAndAndUser_LastName(firstName, lastName);
    }
}
