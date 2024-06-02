package com.school.repositories;

import com.school.models.Mark;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    void findById() {
    }

    @Test
    void findAllByStudentId() {
        Student student = new Student(new User("Id", "Od", "cemnc@idv.fi", "piehvhuoe08475780ldkjfIHFGEROSIg"));
        Subject subject = new Subject("Maths");
        studentRepository.save(student);
        subjectRepository.save(subject);
        markRepository.save(new Mark(1, student, subject));
//        markRepository.save(new Mark(1, student, subject));
//        markRepository.save(new Mark(1, student, subject));
//        markRepository.save(new Mark(1, student, subject));
//        markRepository.save(new Mark(1, student, subject));
//        markRepository.save(new Mark(1, student, subject));
//        markRepository.save(new Mark(1, student, subject));
//        markRepository.save(new Mark(1, student, subject));
//        markRepository.save(new Mark(1, student, subject));
//        markRepository.save(new Mark(1, student, subject));
//        markRepository.save(new Mark(1, student, subject));
        assertFalse(markRepository.findAllByStudentId(student.getId()).isEmpty());
    }

    @Test
    void findAllBySubjectId() {
        Student student = new Student(new User("Id", "Od", "cemnc@idv.fi", "piehvhuoe08475780ldkjfIHFGEROSIg"));
        Subject subject = new Subject("Maths");
        studentRepository.save(student);
        subjectRepository.save(subject);
        markRepository.save(new Mark(1, student, subject));
        assertFalse(markRepository.findAllByStudentId(student.getId()).isEmpty());
    }
}