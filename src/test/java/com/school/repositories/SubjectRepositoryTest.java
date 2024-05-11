package com.school.repositories;

import com.school.models.Subject;
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

    @AfterEach
    void tearDown() {
        subjectRepository.deleteAll();
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
    void notFindAllByOrderByName() {
        //given
        List<Subject> subjects = new ArrayList<>(List.of(
                new Subject("C"),
                new Subject("A"),
                new Subject("B")
        ));
        subjectRepository.saveAll(subjects);

        //when
        List<Subject> result = subjectRepository.findAllByOrderByName().get();
        boolean expected = result.equals(subjects);

        //then
        assertThat(expected).isFalse();
    }

//    @Test
//    void findByTeacher_Id() { // TODO -fix-problem-
//        //given
//        Teacher teacher = new Teacher(new User(
//                "teacher 1",
//                "firstName",
//                "lastName",
//                "12345",
//                "email@gmail.com"));
//        Teacher anotherTeacher = new Teacher(new User(
//                "teacher 2",
//                "firstName",
//                "lastName",
//                "12345",
//                "email@gmail.com"));
//        Subject subject1 = new Subject("Algebra", teacher);
//        Subject subject2 = new Subject("Geometry", teacher);
//        Subject subject3 = new Subject("Philosophy", anotherTeacher);
//
//        List<Subject> subjects = new ArrayList<>(List.of(
//                subject1,
//                subject2,
//                subject3
//        ));
//        subjectRepository.saveAll(subjects);
//
//        //when
//        List<Subject> result = subjectRepository.findByTeacher_Id(teacher.getId()).get();
//
//        //then
//        boolean expected = result.equals(new ArrayList<>(List.of(
//                subject1,
//                subject2
//        )));
//        assertThat(expected).isTrue();
//    }

    @Test
    void findByStudent_Id() { // TODO
        //given
        //when
        //then
    }
}