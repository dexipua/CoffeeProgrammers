package com.school.repositories;

import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private Subject subject1;
    private Subject subject2;

    @BeforeEach
    void setUp() {
        subject1 = new Subject();
        subject1.setName("Math");

        subject2 = new Subject();
        subject2.setName("Philosophy");
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
        subjectRepository.deleteAll();
    }

    @Test
    void findStudentBySubjectName() {
        // Given
        Student student1 = new Student();
        student1.getSubjects().add(subject1);

        Student student2 = new Student();
        student2.getSubjects().add(subject2);

        subjectRepository.saveAll(List.of(subject1, subject2));
        studentRepository.saveAll(List.of(student1, student2));

        // When
        List<Student> res = studentRepository.findStudentBySubjectNameContainingIgnoreCase("Math");
        boolean result = res.equals(List.of(student1));

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void NotFindStudentBySubjectName(){
        // Given

        Student student1 = new Student();
        student1.getSubjects().add(subject1);

        subjectRepository.saveAll(List.of(subject1, subject2));
        studentRepository.save(student1);

        // When
        List<Student> res = studentRepository.findStudentBySubjectNameContainingIgnoreCase("Art");
        boolean result = res.equals(List.of());

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void findAllByOrderByUser() {
        // Given
        Student student1 = new Student(new User("Id", "Dcba", "cemnc@idv.fi", "piehvhuoe08475780ldkjfIHFGEROSIg"));

        Student student2 = new Student(new User("Id", "Abcd", "cemsdnc@idv.fi", "piehvhuoesf08475780ldkjfIHFGEROSIg"));
        studentRepository.save(student1);
        studentRepository.save(student2);

        // When
        List<Student> res = studentRepository.findAllByOrderByUser_LastNameAsc();

        // Then
        assertEquals(res, Arrays.asList(student2, student1));
    }

    @Test
    void findAllByTeacherId() {
        // Given
        Teacher teacher = new Teacher(new User("Teacher", "Bdk", "cemncSd@idv.fi", "piehvhuoe08475780ldkjfdsfIHFGEROSIg"));

        subject1.setTeacher(teacher);
        Student student1 = new Student(new User("Id", "Od", "cemnc@idv.fi", "piehvhuoe08475780ldkjfdsfIHFGEROSIg"));

        Student student2 = new Student(new User("Id", "Ad", "cemsdnc@idv.fi", "piehvhuoe08475780ldkjfdsfIHFGEROSIg"));
        student1.setSubjects(Set.of(subject1));
        student2.setSubjects(Set.of(subject1));

        subjectRepository.save(subject1);
        teacherRepository.save(teacher);
        studentRepository.save(student1);
        studentRepository.save(student2);


        // When
        List<Student> res = studentRepository.findAllByTeacherId(teacher.getId());

        // Then
        assertEquals(res, Arrays.asList(student1, student2));
    }
}


