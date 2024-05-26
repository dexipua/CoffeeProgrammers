package com.school.service.impl;


import com.school.exception.StudentExistException;
import com.school.exception.StudentNotFoundException;
import com.school.models.Student;
import com.school.repositories.RoleRepository;
import com.school.repositories.StudentRepository;
import com.school.service.StudentService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;

    @Override
    public Student create(@NotNull Student student){
        if(studentRepository.findByUserEmail(student.getUser().getEmail()).isPresent()) {
            throw new StudentExistException("Student with such email already exist");
        }
        student.getUser().setRole(roleRepository.findByName("STUDENT").get());
        return studentRepository.save(student);
    }

    @Override
    public Student findById(long id){
        return studentRepository.findById(id).orElseThrow(
                () -> new StudentNotFoundException("Student with id " + id + " not found"));
    }

    @Override
    public Student update(@NotNull Student student){
        if(studentRepository.findByUserEmail(student.getUser().getEmail()).isPresent()) {
            if(!(student.getUser().getEmail().equals(studentRepository.findById(student.getId()).get().getUser().getEmail()))) {
                throw new StudentExistException("Student with such email already exist");
            }
        }
        student.getUser().setRole(roleRepository.findByName("STUDENT").get());
        findById(student.getId());
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(long id){
        Student student = findById(id);
        studentRepository.delete(student);
    }

    @Override
    public List<Student> findAll(){
        List<Student> students = studentRepository.findAll();
        Collections.sort(students);
        return students;
    }

    @Override
    public List<Student> findBySubjectName(String subjectName){
        return studentRepository.findStudentBySubjectsContains(subjectName).orElseThrow(
                () -> new StudentNotFoundException("Student with " + subjectName + " not found"));
    }

    @Override
    public Student findByEmail(String email){
        Optional<Student> student = studentRepository.findByUserEmail(email);
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        return student.get();
    }


}
