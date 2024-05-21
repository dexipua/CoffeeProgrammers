package com.school.service.impl;


import com.school.models.Student;
import com.school.repositories.RoleRepository;
import com.school.repositories.StudentRepository;
import com.school.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;

    @Override
    public Student create(Student student){
        student.getUser().setRole(roleRepository.findByName("STUDENT").get());
        if(student != null){
            return studentRepository.save(student);
        }
        throw new EntityNotFoundException("Student not found");
    }

    @Override
    public Student findById(long id){
        return studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Student with id " + id + " not found"));
    }

    @Override
    public Student update(Student student){
        if (student != null) {
            findById(student.getId());
            student.getUser().setRole(roleRepository.findByName("STUDENT").get());
            return studentRepository.save(student);
        }
        throw new EntityNotFoundException("Student is null");
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
                () -> new EntityNotFoundException("Student with " + subjectName + " not found"));
    }

    @Override
    public Student findByUsername(String username){
        Optional<Student> student = studentRepository.findByUsername(username);
        if (!(student.isPresent())) {
            throw new EntityNotFoundException("Student not found");
        }
        return student.get();
    }

    @Override
    public Student findByEmail(String email){
        Optional<Student> student = studentRepository.findByEmail(email);
        if (!(student.isPresent())) {
            throw new EntityNotFoundException("Student not found");
        }
        return student.get();
    }


}
