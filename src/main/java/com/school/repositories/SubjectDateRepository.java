package com.school.repositories;

import com.school.models.Student;
import com.school.models.SubjectDate;
import com.school.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface SubjectDateRepository extends JpaRepository<SubjectDate, Long> {
    List<SubjectDate> findAllBySubject_Students_Id(long studentId);
    List<SubjectDate> findAllByDayOfWeekAndNumOfLessonAndSubject_Teacher(DayOfWeek dayOfWeek, SubjectDate.NumOfLesson numOfLesson, Teacher teacher);
    List<SubjectDate> findAllByDayOfWeekAndNumOfLessonAndSubject_StudentsContains(DayOfWeek dayOfWeek, SubjectDate.NumOfLesson numOfLesson, Student student);
    List<SubjectDate> findAllByDayOfWeekAndNumOfLessonAndSubject_TeacherAndIdIsNot(DayOfWeek dayOfWeek, SubjectDate.NumOfLesson numOfLesson, Teacher teacher, long id);
    List<SubjectDate> findAllByDayOfWeekAndNumOfLessonAndSubject_StudentsContainsAndIdIsNot(DayOfWeek dayOfWeek, SubjectDate.NumOfLesson numOfLesson, Student student, long id);
}
