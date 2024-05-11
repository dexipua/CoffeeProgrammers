package com.school.repositories;

import com.school.models.Subject;
import com.school.models.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @AfterEach
    void tearDown() {
        subjectRepository.deleteAll();
        teacherRepository.deleteAll();
    }

    @Test
    void findByName() {
        //given
        Subject subject = new Subject("Math");
        subjectRepository.save(subject);

        //when
        Subject result = subjectRepository.findByName("Math").get();
        boolean expected = result.equals(subject);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void notFindByName() {
        //given
        Subject subject = new Subject("Math");
        subjectRepository.save(subject);

        //when
        Optional<Subject> result = subjectRepository.findByName("-----");

        //then
        assertThrows(NoSuchElementException.class, result::get);
    }

    @Test
    void findAllByOrderByName() {
        //given
        List<Subject> subjects = new ArrayList<>(List.of(
                new Subject("C"),
                new Subject("A"),
                new Subject("B")
        ));
        subjectRepository.saveAll(subjects);

        //when
        List<Subject> result = subjectRepository.findAllByOrderByName().get();
        subjects.sort(Comparator.comparing(Subject::getName));
        boolean expected = result.equals(subjects);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void findByTeacher_Id() {
        //given
        Teacher teacher = new Teacher();
        Teacher anotherTeacher = new Teacher();

        Subject subject1 = new Subject("Algebra", teacher);
        Subject subject2 = new Subject("Geometry", teacher);
        Subject subject3 = new Subject("Philosophy", anotherTeacher);

        List<Subject> subjects = new ArrayList<>(List.of(
                subject1,
                subject2,
                subject3
        ));

        teacherRepository.save(teacher);
        teacherRepository.save(anotherTeacher);
        subjectRepository.saveAll(subjects);

        //when
        List<Subject> result = subjectRepository.findByTeacher_Id(teacher.getId()).get();
        boolean expected = result.equals(new ArrayList<>(List.of(
                subject1,
                subject2
        )));

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void findByStudent_Id() { // TODO
        //given
        //when
        //then
    }
}