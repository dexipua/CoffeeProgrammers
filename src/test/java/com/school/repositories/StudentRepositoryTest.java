package com.school.repositories;

import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
        subjectRepository.deleteAll();
    }

    @Test
    void findStudentBySubjectName() {
        // Given
        Subject subject1 = new Subject("Math");
        Subject subject2 = new Subject("Art");

        Student student1 = new Student();
        student1.setSubjects(new ArrayList<>(List.of(subject1)));

        Student student2 = new Student();
        student2.setSubjects(new ArrayList<>(List.of(subject2)));

        subjectRepository.saveAll(List.of(subject1, subject2));
        studentRepository.saveAll(List.of(student1, student2));

        // When
        List<Student> res = studentRepository.findStudentBySubjectNameContaining("Math");
        boolean result = res.equals(List.of(student1));

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void NotFindStudentBySubjectName(){
        // Given
        Subject subject1 = new Subject("Math");
        Subject subject2 = new Subject("Art");

        Student student1 = new Student();
        student1.setSubjects(new ArrayList<>(List.of(subject1)));


        subjectRepository.saveAll(List.of(subject1, subject2));
        studentRepository.save(student1);

        // When
        List<Student> res = studentRepository.findStudentBySubjectNameContaining("Art");
        boolean result = res.equals(List.of());

        // Then
        assertThat(result).isTrue();

    }

    @Test
    void findByEmail() {
        // Given
        Student student = new Student(new User("Artem", "Moseichenko",  "am@gmil.com","Abekpr257"));

        studentRepository.save(student);

        // When
        Student res = studentRepository.findByUserEmail("am@gmil.com").get();
        boolean result = res.equals(student);

        // Then
        assertThat(result).isTrue();

    }

    @Test
    void NotFindByEmail() {
        //given
        Student student = new Student(new User("Artem", "Moseichenko",  "am@gmil.com","Abekpr257"));

        studentRepository.save(student);

        //then
        Optional<Student> res = studentRepository.findByUserEmail("sgfdfsd@gkf.com");
        Assertions.assertThatExceptionOfType(NoSuchElementException.class)
                //when
                .isThrownBy(res::get);


    }
}


