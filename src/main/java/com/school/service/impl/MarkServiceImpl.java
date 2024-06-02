package com.school.service.impl;

import com.school.models.Mark;
import com.school.models.Student;
import com.school.models.StudentNews;
import com.school.models.Subject;
import com.school.repositories.MarkRepository;
import com.school.service.MarkService;
import com.school.service.StudentNewsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;
    private final StudentNewsService studentNewsService;

    @Override
    public Mark create(Mark mark) {
        studentNewsService.create(new StudentNews(
                "Ви отримали нову оцінку " + mark.getMark() + " з предмету: " + mark.getSubject().getName(),
                mark.getStudent(), mark.getTime())
        );
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
        mark.setSubject(oldMark.getSubject());
        mark.setStudent(oldMark.getStudent());
        studentNewsService.create(new StudentNews(
                "Оцінка змінена на " + mark.getMark() + " з предмету: " + mark.getSubject().getName(),
                mark.getStudent(), mark.getTime())
        );
        return markRepository.save(mark);
    }

    @Override
    public void delete(long id) {
        Mark mark = findById(id);
        markRepository.delete(mark);
    }

    @Override
    public HashMap<Subject, List<Mark>> findAllByStudentId(long studentId) {
        HashMap<Subject, List<Mark>> result = new HashMap<>();
        List<Mark> temp;
        for (Mark mark : markRepository.findAllByStudentId(studentId)) {
            temp = result.getOrDefault(mark.getSubject(), new ArrayList<>());
            temp.add(mark);
            result.put(mark.getSubject(), temp);
        }
        return result;
    }

    @Override
    public HashMap<Student, List<Mark>> findAllBySubjectId(long subjectId) {
        HashMap<Student, List<Mark>> result = new HashMap<>();
        List<Mark> temp;
        for (Mark mark : markRepository.findAllBySubjectId(subjectId)) {
            temp = result.getOrDefault(mark.getStudent(), new ArrayList<>());
            temp.add(mark);
            result.put(mark.getStudent(), temp);
        }
        return result;
    }
}
