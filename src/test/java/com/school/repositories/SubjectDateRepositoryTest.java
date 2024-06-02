package com.school.repositories;

import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.SubjectDate;
import com.school.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class SubjectDateRepositoryTest {
    @Autowired
    private SubjectDateRepository subjectDateRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void findAllByDayOfWeekOrderByNumOfLesson() {
        Subject subject = new Subject("Maths");
        Student student = new Student(new User("Hefef", "Ufeihfeinfe", "gewudgqw@deu.fj", "ljffehfoiehfohekfjjeifipjpkfipjeHFHOhfehfhoehofohe87237483749387434324"));
        student.setSubjects(List.of(subject));
        subjectRepository.save(subject);
        studentRepository.save(student);
        SubjectDate subjectDate = new SubjectDate(subject, DayOfWeek.MONDAY, "FIRST");
        SubjectDate subjectDate2 = new SubjectDate(subject, DayOfWeek.SUNDAY, "SECOND");
        SubjectDate subjectDate3 = new SubjectDate(subject, DayOfWeek.SATURDAY, "THIRD");
        subjectDateRepository.save(subjectDate);
        subjectDateRepository.save(subjectDate2);
        subjectDateRepository.save(subjectDate3);
        List<SubjectDate> temp = new ArrayList<>(Arrays.asList(subjectDate, subjectDate2, subjectDate3));
        assertEquals(subjectDateRepository.findAllBySubject_Students_Id(student.getId()), temp);
    }
}