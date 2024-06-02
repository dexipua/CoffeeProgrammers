package com.school.service.impl;

import com.school.models.Subject;
import com.school.models.SubjectDate;
import com.school.repositories.SubjectDateRepository;
import com.school.service.SubjectDateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class SubjectDateServiceImpl implements SubjectDateService {

    private final SubjectDateRepository subjectDateRepository;

    @Override
    public SubjectDate create(SubjectDate subjectDate) {
        return subjectDateRepository.save(subjectDate);
    }

    @Override
    public SubjectDate update(SubjectDate subjectDate) {
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
                new EntityNotFoundException("Subject Date Not Found"));
    }

    @Override
    public HashMap<DayOfWeek, HashMap<SubjectDate.NumOfLesson, Subject>> findAllBySubject_Students_Id(long studentId) {
        HashMap<DayOfWeek, HashMap<SubjectDate.NumOfLesson, Subject>> result = new HashMap<>();
        List<HashMap<SubjectDate.NumOfLesson, Subject>> temp = new ArrayList<>();
        for(SubjectDate subjectDate : subjectDateRepository.findAllBySubject_Students_Id(studentId)) {
            result.getOrDefault(subjectDate.getDayOfWeek(), new HashMap<>());

        }
    }
}
