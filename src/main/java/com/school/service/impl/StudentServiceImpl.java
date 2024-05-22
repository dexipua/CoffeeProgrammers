package com.school.service.impl;


import com.school.models.Student;
import com.school.repositories.RoleRepository;
import com.school.repositories.StudentRepository;
import com.school.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student create(@NotNull Student student){
        return studentRepository.save(student);
    }

    @Override
    public Student findById(long id){
        return studentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Student with id " + id + " not found"));
    }

    @Override
    public Student update(@NotNull Student student){
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
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findBySubjectName(String subjectName){
        return studentRepository.findBySubjectName(subjectName).orElseThrow(
                () -> new IllegalArgumentException("Student with " + subjectName + " not found"));
    }

    @Override
    public Student findByUsername(String username){
        Optional<Student> student = studentRepository.findByUsername(username);
        if (!(student.isPresent())) {
            throw new IllegalArgumentException("Student not found");
        }
        return student.get();
    }

    @Override
    public Student findByEmail(String email){
        Optional<Student> student = studentRepository.findByEmail(email);
        if (!(student.isPresent())) {
            throw new IllegalArgumentException("Student not found");
        }
        return student.get();
    }


}
