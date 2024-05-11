package com.school.repositories;

import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void findBySubjectName() {
        // Given
        Subject mathSubject = new Subject("Mathematics");
        Subject artSubject = new Subject("Art");

        Student student1 = new Student();
        student1.setSubjects(new ArrayList<>(List.of(mathSubject)));

        Student student2 = new Student();
        student2.setSubjects(new ArrayList<>(List.of(mathSubject)));

        Student student3 = new Student();
        student3.setSubjects(new ArrayList<>(List.of(artSubject)));

        subjectRepository.save(artSubject);
        subjectRepository.save(mathSubject);
        studentRepository.saveAll(List.of(student1, student2));

        // When
        List<Student> res = studentRepository.findBySubjectName("Mathematics").get();
        boolean result = res.equals(List.of(student1, student2));

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void NotFindBySubjectName(){
        // Given
        Subject mathSubject = new Subject("Mathematics");
        Subject algebraSubject = new Subject("algebra");

        Student student1 = new Student();
        student1.setSubjects(new ArrayList<>(List.of(algebraSubject)));

        Student student2 = new Student();
        student2.setSubjects(new ArrayList<>(List.of(mathSubject)));

        Student student3 = new Student();
        student3.setSubjects(new ArrayList<>(List.of(algebraSubject)));

        subjectRepository.save(algebraSubject);
        subjectRepository.save(mathSubject);
        studentRepository.saveAll(List.of(student1, student2));

        // When
        List<Student> res = studentRepository.findBySubjectName("dgdsgs").get();
        boolean result = res.equals(List.of());

        // Then
        assertThat(result).isTrue();

    }

    @Test
    void findByEmail() {
        // Given
        Student student = new Student(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));

        studentRepository.save(student);

        // When
        Student res = studentRepository.findByEmail("vladobrod@gmail.com").get();
        boolean result = res.equals(student);

        // Then
        assertThat(result).isTrue();

    }

    @Test
    void NotFindByEmail() {
        //given
        Student student = new Student(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));

        studentRepository.save(student);

        //then
        Optional<Student> res = studentRepository.findByEmail("sgfdfsd@gkf.com");
        assertThrows(NoSuchElementException.class, () -> {
            Student value = res.get();
        });

    }

    @Test
    void findByUsername() {
        // Given
        Student student = new Student(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        studentRepository.save(student);

        // When
        Student res = studentRepository.findByUsername("Vladobrod").get();

        boolean result = res.equals(student);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void NotFindByUserName() {
        //given
        Student student = new Student(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        studentRepository.save(student);

        //then
        Optional<Student> res = studentRepository.findByUsername("fsgdfhs");
        assertThrows(NoSuchElementException.class, () -> {
            Student value = res.get();
        });

    }
}


