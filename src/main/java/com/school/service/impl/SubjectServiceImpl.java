package com.school.service.impl;

import com.school.models.Subject;
import com.school.repositories.SubjectRepository;
import com.school.service.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<List<Subject>> subjects = subjectRepository.findAllByOrderByName();
        return subjects.orElseGet(ArrayList::new);
    }

    @Override
    public Subject findByName(String name) {
        Optional<Subject> subject = subjectRepository.findByName(name);
        if (subject.isPresent()) {
            return subject.get();
        } else {
            throw new EntityNotFoundException("Subject not found");
        }
    }

    @Override
    public List<Subject> findByTeacher_Id(long teacherId) {
        Optional<List<Subject>> subjects = subjectRepository.findByTeacher_Id(teacherId);
        if (subjects.isPresent()) {
            return subjects.get();
        } else {
            throw new EntityNotFoundException("Subjects with teacher id " + teacherId + " not found");
        }
    }

    @Override
    public List<Subject> findByStudent_Id(long studentId) {
        Optional<List<Subject>> subjects = subjectRepository.findByStudent_Id(studentId);
        if (subjects.isPresent()) {
            return subjects.get();
        } else {
            throw new EntityNotFoundException("Subjects with student id " + studentId + " not found");
        }
    }
}
