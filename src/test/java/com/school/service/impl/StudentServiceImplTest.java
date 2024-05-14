package com.school.service.impl;

import com.school.models.Student;
import com.school.models.User;
import com.school.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(studentRepository);
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void create() {
        //Given & When & Then
        studentService.create(new Student(new User("Userrrr", "Vadym", "Honcharuk", "User123", "useruser@gmail.com")));
    }

    @Test
    void findByExitingId() {
        //Given
        long id = 1;
        Student student = new Student();
        when(studentRepository.findById(eq(id))).thenReturn(Optional.of(student));

        //When
        Student foundStudent = studentService.findById(id);

        //Then
        assertEquals(student, foundStudent);
    }

    @Test
    void findByNotExitingId() {
        //Given
        when(studentRepository.findById(-1L)).thenReturn(Optional.empty());

        //When & Then
        assertThrows(EntityNotFoundException.class, () -> studentService.findById(-1L));
    }

    @Test
    void update_Success() {
        // Given
        long studentId = 1;
        Student student = new Student();
        student.setId(studentId);

        when(studentRepository.findById(eq(studentId))).thenReturn(Optional.of(student));

        // When
        Student updatedStudent = new Student();
        updatedStudent.setId(studentId);
        when(studentRepository.save(eq(updatedStudent))).thenReturn(updatedStudent);
        Student result = studentService.update(updatedStudent);

        // Then
        assertEquals(updatedStudent, result);
    }

    @Test
    void update_StudentNotFound(){
        // Given
        long id = -1;
        Student student = new Student();
        student.setId(id);
        when(studentRepository.findById(eq(id))).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> studentService.update(student));


    }

    @Test
    void deleteById_Success() {
        // Given
        long id = 1;
        Student student = new Student();
        student.setId(id);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        // When
        studentService.deleteById(id);

        // Then
        verify(studentRepository).findById(id);
        verify(studentRepository).delete(student);
    }

    @Test
    void deleteById_StudentNotFound() {
        // Given
        long id = -1;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> studentService.deleteById(id));
    }

    @Test
    void findAll(){
        // Given
        List<Student> students = Arrays.asList(new Student(), new Student());
        when(studentRepository.findAll()).thenReturn(students);

        // When
        List<Student> result = studentService.findAll();

        // Then
        assertEquals(students, result);

    }

    @Test
    void findBySubjectName_Exists() {
        // Given
        String subjectName = "Mathematics";
        Student student = new Student();
        when(studentRepository.findBySubjectName(eq(subjectName))).thenReturn(Optional.of(List.of(student)));

        // When
        List<Student> result = studentService.findBySubjectName(subjectName);

        // Then
        assertEquals(student, result.get(0));
    }

    @Test
    void findBySubjectName_NotExists() {
        // Given
        String subjectName = "Physics";
        when(studentRepository.findBySubjectName(eq(subjectName))).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> studentService.findBySubjectName(subjectName));
    }

    @Test
    void findByUsername_Exists() {
        // Given
        String username = "Username";
        Student student = new Student();
        when(studentRepository.findByUsername(eq(username))).thenReturn(Optional.of(student));

        // When
        Student result = studentService.findByUsername(username);

        // Then
        assertEquals(student, result);
    }

    @Test
    void findByUsername_NotExists() {
        // Given
        String username = "NotExistingUsername";
        when(studentRepository.findByUsername(eq(username))).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> studentService.findByUsername(username));
    }

    @Test
    void findByEmail_Exists() {
        // Given
        String email = "useruser@gmail.com";
        Student student = new Student();
        when(studentRepository.findByEmail(eq(email))).thenReturn(Optional.of(student));

        // When
        Student result = studentService.findByEmail(email);

        // Then
        assertEquals(student, result);
    }

    @Test
    void findByEmail_NotExists() {
        // Given
        String email = "notexisting@hhh.kkk";
        when(studentRepository.findByEmail(eq(email))).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> studentService.findByEmail(email));
    }

}

