package com.school.service.impl;

import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.SubjectDate;
import com.school.models.UserNews;
import com.school.repositories.SubjectDateRepository;
import com.school.service.SubjectDateService;
import com.school.service.UserNewsService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectDateServiceImpl implements SubjectDateService {

    private final SubjectDateRepository subjectDateRepository;
    private final UserNewsService userNewsService;

    @Override
    public SubjectDate create(SubjectDate subjectDate) {
        List<SubjectDate> subjectsWithTheSameDateAndTime =
                subjectDateRepository.findAll().stream()
                        .filter(subjectDate1 ->
                                subjectDate1.getDayOfWeek().equals(subjectDate.getDayOfWeek())
                                        && subjectDate1.getNumOfLesson().equals(subjectDate.getNumOfLesson())).toList();

        if (!subjectsWithTheSameDateAndTime.stream()
                .filter(subjectDateSame -> subjectDate.getSubject().getTeacher().equals(subjectDateSame.getSubject().getTeacher()))
                .toList()
                .isEmpty()) {
            throw new EntityExistsException("Subject time with the same option already exists(TEACHER)");
        }

        for (Student student : subjectDate.getSubject().getStudents()) {
            if (!subjectsWithTheSameDateAndTime.stream()
                    .filter(subjectDateSame -> subjectDateSame.getSubject().getStudents().contains(student))
                    .toList()
                    .isEmpty()) {
                throw new EntityExistsException("Subject time with the same option already exists(STUDENT)");
            }
        }
        for(Student student : subjectDate.getSubject().getStudents()) {
            userNewsService.create(new UserNews(subjectDate.getSubject().getName() + "have been set at time "
            + subjectDate.getDayOfWeek().toString() + " " + subjectDate.getNumOfLesson().toString(), student.getUser()));
        }
        return subjectDateRepository.save(subjectDate);
    }


    @Override
    public SubjectDate update(SubjectDate subjectDate) {
        List<SubjectDate> subjectsWithTheSameDateAndTime =
                new ArrayList<>(
                        subjectDateRepository.findAll().stream()
                                .filter(subjectDate1 ->
                                        subjectDate1.getDayOfWeek().equals(subjectDate.getDayOfWeek())
                                                && subjectDate1.getNumOfLesson().equals(subjectDate.getNumOfLesson())
                                                && subjectDate1.getId() != subjectDate.getId()
                                )
                                .toList()
                );

        if (!subjectsWithTheSameDateAndTime.stream()
                .filter(subjectDateSame -> subjectDate.getSubject().getTeacher().equals(subjectDateSame.getSubject().getTeacher()))
                .toList()
                .isEmpty()) {
            throw new EntityExistsException("Subject time with the same option already exists(TEACHER)");
        }

        for (Student student : subjectDate.getSubject().getStudents()) {
            if (!subjectsWithTheSameDateAndTime.stream()
                    .filter(subjectDateSame -> subjectDateSame.getSubject().getStudents().contains(student))
                    .toList()
                    .isEmpty()) {
                throw new EntityExistsException("Subject time with the same option already exists(STUDENT)");
            }
        }
        for(Student student : subjectDate.getSubject().getStudents()) {
            userNewsService.create(new UserNews(subjectDate.getSubject().getName() + "have been reset at time "
                    + subjectDate.getDayOfWeek().toString() + " " + subjectDate.getNumOfLesson().toString(), student.getUser()));
        }
        return subjectDateRepository.save(subjectDate);
    }
    @Override
    public void delete(SubjectDate subjectDate) {
        findById(subjectDate.getId());
        subjectDateRepository.delete(subjectDate);
    }

    @Override
    public SubjectDate findById(long id) {
        return subjectDateRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Subject Date with id " + id + " Not Found"));
    }

    @Override
    public HashMap<DayOfWeek, HashMap<SubjectDate.NumOfLesson, Subject>> findAllBySubject_Students_Id(long studentId) {
        HashMap<DayOfWeek, HashMap<SubjectDate.NumOfLesson, Subject>> result = new HashMap<>();
        HashMap<SubjectDate.NumOfLesson, Subject> tempMap;
        for(SubjectDate subjectDate : subjectDateRepository.findAllBySubject_Students_Id(studentId)) {
            tempMap = result.getOrDefault(subjectDate.getDayOfWeek(), new HashMap<>());
            tempMap.put(subjectDate.getNumOfLesson(), subjectDate.getSubject());
            result.put(subjectDate.getDayOfWeek(), tempMap);
        }
        return result;
    }
}
