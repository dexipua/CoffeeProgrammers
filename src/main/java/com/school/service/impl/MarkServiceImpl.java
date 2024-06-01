package com.school.service.impl;

import com.school.models.Mark;
import com.school.repositories.MarkRepository;
import com.school.service.MarkService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    @Override
    public Mark create(Mark mark) {
        return markRepository.save(mark);
    }

    @Override
    public Mark findById(long id) {
        return markRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Mark with id " + id + " not found"));
    }

    @Override
    public Mark update(@NotNull Mark mark) {
        Mark oldMark = findById(mark.getId());
        mark.setTime(LocalDateTime.now());
        if(mark.getStatus() == null || mark.getStatus().isEmpty()) {
            mark.setStatus(oldMark.getStatus());
        }
        mark.setSubject(oldMark.getSubject());
        mark.setStudent(oldMark.getStudent());
        return markRepository.save(mark);
    }

    @Override
    public void delete(long id) {
        Mark mark = findById(id);
        markRepository.delete(mark);
    }

    @Override
    public List<Mark> findAllByStudentId(long studentId) {
        return markRepository.findAllByStudentId(studentId);
    }

    @Override
    public List<Mark> findAllBySubjectId(long subjectId) {
        return markRepository.findAllBySubjectId(subjectId);
    }

    @Override
    public List<Mark> findAllByFirstNameAndAndLastName(String firstName, String lastName) {
        return markRepository.findAllByStudent_User_FirstNameContainingAndStudent_User_LastNameContaining(firstName, lastName);
    }

    @Override
    public List<Mark> findAllBySubjectName(String subjectName) {
        return markRepository.findAllBySubject_Name(subjectName);
    }

    @Override
    public List<Mark> findAllByStudentIdAndSubjectId(long studentId, long subjectId) {
        return markRepository.findAllByStudentIdAndSubjectId(studentId, subjectId);
    }

    @Override
    public List<Mark> findAllByStudentNameAndSubjectName(String firstName, String lastName, String subjectName) {
        return markRepository.findAllByStudent_User_FirstNameContainingAndStudent_User_LastNameContainingAndSubject_NameContaining(firstName, lastName, subjectName);
    }
}
