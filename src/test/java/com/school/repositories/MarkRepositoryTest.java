package com.school.repositories;

import com.school.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MarkRepositoryTest {
    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    void findAllByStudentId() {
        Student student = new Student(new User("Id", "Od", "cemnc@idv.fi", "piehvhuoe08475780ldkjfIHFGEROSIg"));
        Subject subject = new Subject("Maths");
        studentRepository.save(student);
        subjectRepository.save(subject);
        markRepository.save(new Mark(1, student, subject));
        markRepository.save(new Mark(1, student, subject));
        markRepository.save(new Mark(1, student, subject));
        assertEquals(3, markRepository.findAllByStudent_Id(student.getId()).size());
    }

    @Test
    void findAllBySubject_Id() {
        Student student = new Student(new User("Id", "Od", "cemnc@idv.fi", "piehvhuoe08475780ldkjfIHFGEROSIg"));
        Subject subject = new Subject("Maths");
        studentRepository.save(student);
        subjectRepository.save(subject);
        markRepository.save(new Mark(1, student, subject));
        markRepository.save(new Mark(2, student, subject));
        markRepository.save(new Mark(3, student, subject));
        assertEquals(3, markRepository.findAllByStudent_Id(student.getId()).size());
    }

    @Test
    void findById() {
        Student student = new Student(new User("Id", "Od", "cemnc@idv.fi", "piehvhuoe08475780ldkjfIHFGEROSIg"));
        Subject subject = new Subject("Maths");
        studentRepository.save(student);
        subjectRepository.save(subject);
        Mark mark = new Mark(1, student, subject);
        markRepository.save(mark);
        assertEquals(mark, markRepository.findById(mark.getId()).get());
    }

    @Test
    void NotFindById() {
        Student student = new Student(new User("Id", "Od", "cemnc@idv.fi", "piehvhuoe08475780ldkjfIHFGEROSIg"));
        Subject subject = new Subject("Maths");
        studentRepository.save(student);
        subjectRepository.save(subject);
        Mark mark = new Mark(1, student, subject);
        markRepository.save(mark);

        //then
        Optional<Mark> res = markRepository.findById(-1);
        assertThrows(NoSuchElementException.class, res::get);
    }
}