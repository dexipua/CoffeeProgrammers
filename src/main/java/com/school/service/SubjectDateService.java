package com.school.service;

import com.school.models.Subject;
import com.school.models.SubjectDate;

import java.time.DayOfWeek;
import java.util.TreeMap;

public interface SubjectDateService {
    SubjectDate create(SubjectDate subjectDate);
    SubjectDate update(SubjectDate subjectDate);
    void delete(SubjectDate subjectDate);
    void deleteAllBySubjectId(long subjectId);
    SubjectDate findById(long id);
    TreeMap<DayOfWeek, TreeMap<SubjectDate.NumOfLesson, Boolean>> findAllBySubject_Id(long subjectId);
    TreeMap<DayOfWeek, TreeMap<SubjectDate.NumOfLesson, Subject>> findAllBySubject_Students_Id(long studentId);
}
