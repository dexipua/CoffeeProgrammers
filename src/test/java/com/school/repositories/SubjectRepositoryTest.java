package com.school.repositories;

import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        subjectRepository.deleteAll();
        teacherRepository.deleteAll();
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
        List<Subject> result = subjectRepository.findAllByOrderByName();
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
        List<Subject> result = subjectRepository.findByTeacher_Id(teacher.getId());
        boolean expected = result.equals(new ArrayList<>(List.of(
                subject1,
                subject2
        )));

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void notFindByTeacher_Id() {
        //given
        Teacher teacher = new Teacher();
        Teacher anotherTeacher = new Teacher();

        Subject subject1 = new Subject("Algebra", anotherTeacher);
        Subject subject2 = new Subject("Geometry", anotherTeacher);

        List<Subject> subjects = new ArrayList<>(List.of(
                subject1,
                subject2
        ));

        teacherRepository.save(teacher);
        teacherRepository.save(anotherTeacher);
        subjectRepository.saveAll(subjects);

        //when
        List<Subject> result = subjectRepository.findByTeacher_Id(teacher.getId());
        boolean expected = result.equals(new ArrayList<>());

        //then
        assertThat(expected).isTrue();
    }
    @Test
    void findByStudent_Id() {
        //given
        Student student = new Student();
        Student anotherStudent = new Student();

        Subject subject1 = new Subject("Algebra");
        Subject subject2 = new Subject("Geometry");
        Subject subject3 = new Subject("Philosophy");

        subject1.getStudents().add(student);
        subject2.getStudents().add(anotherStudent);
        subject3.getStudents().addAll(List.of(
                student, anotherStudent
        ));

        List<Subject> subjects = new ArrayList<>(List.of(
                subject1,
                subject2,
                subject3
        ));

        studentRepository.save(student);
        studentRepository.save(anotherStudent);
        subjectRepository.saveAll(subjects);

        //when
        List<Subject> result = subjectRepository.findByStudent_Id(student.getId());
        boolean expected = result.equals(new ArrayList<>(List.of(
                subject1,
                subject3
        )));

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void notFindByStudent_Id() {
        //given
        Student student = new Student();
        Student anotherStudent = new Student();

        Subject subject1 = new Subject("Algebra");
        Subject subject2 = new Subject("Geometry");

        subject1.getStudents().add(anotherStudent);
        subject2.getStudents().add(anotherStudent);

        List<Subject> subjects = new ArrayList<>(List.of(
                subject1,
                subject2
        ));

        studentRepository.save(student);
        studentRepository.save(anotherStudent);
        subjectRepository.saveAll(subjects);

        //when
        List<Subject> result = subjectRepository.findByStudent_Id(student.getId());
        boolean expected = result.equals(new ArrayList<>());

        //then
        assertThat(expected).isTrue();
    }
}