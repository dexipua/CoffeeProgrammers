package com.school.repositories;

import com.school.models.Student;
import com.school.models.SubjectDate;
import com.school.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface SubjectDateRepository extends JpaRepository<SubjectDate, Long> {
    void deleteAllBySubjectId(long subjectId);
    List<SubjectDate> findAllBySubject_Students_Id(long studentId);
    List<SubjectDate> findAllBySubject_Id(long subjectId);
    List<SubjectDate> findAllByDayOfWeekAndNumOfLesson(DayOfWeek dayOfWeek, SubjectDate.NumOfLesson numOfLesson);
    List<SubjectDate> findAllByDayOfWeekAndNumOfLessonAndSubject_Teacher(DayOfWeek dayOfWeek, SubjectDate.NumOfLesson numOfLesson, Teacher teacher);
    List<SubjectDate> findAllByDayOfWeekAndNumOfLessonAndSubject_StudentsContains(DayOfWeek dayOfWeek, SubjectDate.NumOfLesson numOfLesson, Student student);}
