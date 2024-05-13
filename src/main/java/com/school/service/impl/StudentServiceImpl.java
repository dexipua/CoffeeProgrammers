package com.school.service.impl;


import com.school.models.Student;
import com.school.repositories.StudentRepository;
import com.school.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student create(Student student){
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
        if(student != null){
            student = findById(student.getId());
            studentRepository.save(student);
        }
        throw new EntityNotFoundException("Student with id " + student.getId() + " not found");
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
        Student student = studentRepository.findByUsername(username).get();
        if (student == null) {
            throw new EntityNotFoundException("Student not found");
        }
        return student;
    }

    @Override
    public Student findByEmail(String email){
        Student student = studentRepository.findByEmail(email).get();
        if (student == null) {
            throw new EntityNotFoundException("Student not found");
        }
        return student;
    }


}
