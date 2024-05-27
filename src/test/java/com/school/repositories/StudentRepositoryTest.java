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
    void findStudentBySubjectsContains() {
        // Given
        Subject mathSubject = new Subject("Mathematics");
        Subject artSubject = new Subject("Art");

        Student student1 = new Student();
        student1.setSubjects(new ArrayList<>(List.of(mathSubject)));

        Student student2 = new Student();
        student2.setSubjects(new ArrayList<>(List.of(mathSubject)));

        subjectRepository.saveAll(List.of(mathSubject, artSubject));
        studentRepository.saveAll(List.of(student1, student2));

        // When
        List<Student> res = studentRepository.findStudentBySubjectsContains("Mathematics").get();
        boolean result = res.equals(List.of(student1, student2));

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void NotFindStudentBySubjectsContains(){
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
        List<Student> res = studentRepository.findStudentBySubjectsContains("dgdsgs").get();
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
        assertThrows(NoSuchElementException.class, () -> {
            Student value = res.get();
        });

    }
}


