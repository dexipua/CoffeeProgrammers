package com.school.service.impl;

import com.school.models.Subject;
import com.school.repositories.SubjectRepository;
import com.school.service.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public Subject create(Subject subject) {
        if (subject != null) {
            return subjectRepository.save(subject);
        } else {
            throw new EntityNotFoundException("Subject not found");
        }
    }

    @Override
    public Subject readById(long id) {
        return subjectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Subject with id " + id + " not found"));
    }

    @Override
    public Subject update(Subject subject) {
        if (subject != null) {
            readById(subject.getId());
            return subjectRepository.save(subject);
        } else {
            throw new EntityNotFoundException("Subject not found");
        }
    }

    @Override
    public void delete(long id) {
        Subject subject = readById(id);
        subjectRepository.delete(subject);
    }

    @Override
    public List<Subject> getAllByOrderByName() {
        if (subjectRepository.findAllByOrderByName().isPresent()) {
            return subjectRepository.findAllByOrderByName().get();
        } else {
            throw new EntityNotFoundException("Subjects not found");
        }
    }

    @Override
    public Subject findByName(String name) {
        if (subjectRepository.findByName(name).isPresent()) {
            return subjectRepository.findByName(name).get();
        } else {
            throw new EntityNotFoundException("Subject with name " + name + " not found");
        }
    }

    @Override
    public List<Subject> findByTeacher_Id(long teacherId) {
        if (subjectRepository.findByTeacher_Id(teacherId).isPresent()) {
            return subjectRepository.findByTeacher_Id(teacherId).get();
        } else {
            throw new EntityNotFoundException("Subjects with teacher id " + teacherId + " not found");
        }
    }

    @Override
    public List<Subject> findByStudent_Id(long studentId) {
        if (subjectRepository.findByTeacher_Id(studentId).isPresent()) {
            return subjectRepository.findByTeacher_Id(studentId).get();
        } else {
            throw new EntityNotFoundException("Subjects with teacher id " + studentId + " not found");
        }
    }
}
