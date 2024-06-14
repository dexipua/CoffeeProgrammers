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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class SubjectDateServiceImpl implements SubjectDateService {

    private final SubjectDateRepository subjectDateRepository;
    private final UserNewsService userNewsService;

    @Override
    public SubjectDate create(SubjectDate subjectDate) {
        if(!subjectDateRepository.findAllByDayOfWeekAndNumOfLessonAndSubject_Teacher(
                subjectDate.getDayOfWeek(),
                subjectDate.getNumOfLesson(),
                subjectDate.getSubject().getTeacher()).isEmpty()){
            throw new EntityExistsException("Subject time with the same option already exists(TEACHER)");
        }
        for (Student student : subjectDate.getSubject().getStudents()){
            if (!subjectDateRepository.findAllByDayOfWeekAndNumOfLessonAndSubject_StudentsContains(
                    subjectDate.getDayOfWeek(),
                    subjectDate.getNumOfLesson(),
                    student
            ).isEmpty()){
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
        if(!subjectDateRepository.findAllByDayOfWeekAndNumOfLessonAndSubject_TeacherAndIdIsNot(
                subjectDate.getDayOfWeek(),
                subjectDate.getNumOfLesson(),
                subjectDate.getSubject().getTeacher(),
                subjectDate.getId()).isEmpty()){
            throw new EntityExistsException("Subject time with the same option already exists(TEACHER)");
        }
        for (Student student : subjectDate.getSubject().getStudents()){
            if (!subjectDateRepository.findAllByDayOfWeekAndNumOfLessonAndSubject_StudentsContainsAndIdIsNot(
                    subjectDate.getDayOfWeek(),
                    subjectDate.getNumOfLesson(),
                    student,
                    subjectDate.getId()
            ).isEmpty()){
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

    @Transactional
    @Override
    public void deleteAllBySubjectId(long subjectId) {
        subjectDateRepository.deleteAllBySubjectId(subjectId);
    }

    @Override
    public SubjectDate findById(long id) {
        return subjectDateRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Subject Date with id " + id + " Not Found"));
    }

    @Override
    public TreeMap<DayOfWeek, TreeMap<SubjectDate.NumOfLesson, Subject>> findAllBySubject_Students_Id(long studentId) {
        TreeMap<DayOfWeek, TreeMap<SubjectDate.NumOfLesson, Subject>> result = new TreeMap<>();
        TreeMap<SubjectDate.NumOfLesson, Subject> tempMap;
        for(DayOfWeek dayOfWeek : DayOfWeek.values()) {
            TreeMap<SubjectDate.NumOfLesson, Subject> temp = result.getOrDefault(dayOfWeek, new TreeMap<>());
            for(SubjectDate.NumOfLesson numOfLesson : SubjectDate.NumOfLesson.values()) {
                temp.put(numOfLesson, null);
            }
            result.put(dayOfWeek, temp);
        }
        for(SubjectDate subjectDate : subjectDateRepository.findAllBySubject_Students_Id(studentId)) {
            tempMap = result.getOrDefault(subjectDate.getDayOfWeek(), new TreeMap<>());
            tempMap.put(subjectDate.getNumOfLesson(), subjectDate.getSubject());
            result.put(subjectDate.getDayOfWeek(), tempMap);
        }
        return result;
    }
    @Override
    public TreeMap<DayOfWeek, TreeMap<SubjectDate.NumOfLesson, Boolean>> findAllBySubject_Id(long subjectId) {
        TreeMap<DayOfWeek, TreeMap<SubjectDate.NumOfLesson, Boolean>> result = new TreeMap<>();
        TreeMap<SubjectDate.NumOfLesson, Boolean> tempMap;
        for(DayOfWeek dayOfWeek : DayOfWeek.values()) {
            TreeMap<SubjectDate.NumOfLesson, Boolean> temp = result.getOrDefault(dayOfWeek, new TreeMap<>());
            for(SubjectDate.NumOfLesson numOfLesson : SubjectDate.NumOfLesson.values()) {
                temp.put(numOfLesson, false);
            }
            result.put(dayOfWeek, temp);
        }
        for(SubjectDate subjectDate : subjectDateRepository.findAllBySubject_Id(subjectId)) {
            tempMap = result.getOrDefault(subjectDate.getDayOfWeek(), new TreeMap<>());
            tempMap.put(subjectDate.getNumOfLesson(), true);
            result.put(subjectDate.getDayOfWeek(), tempMap);
        }
        return result;
    }
}
