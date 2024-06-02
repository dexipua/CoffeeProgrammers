package com.school.service;

import com.school.models.Subject;
import com.school.models.SubjectDate;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;

public interface SubjectDateService {
    SubjectDate create(SubjectDate subjectDate);
    SubjectDate update(SubjectDate subjectDate);
    void delete(SubjectDate subjectDate);
    SubjectDate findById(long id);
    HashMap<DayOfWeek, HashMap<SubjectDate.NumOfLesson, Subject>> findAllBySubject_Students_Id(long studentId);
}
