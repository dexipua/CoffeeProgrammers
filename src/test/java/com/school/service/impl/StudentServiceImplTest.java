package com.school.service.impl;

import com.school.exception.StudentExistException;
import com.school.exception.StudentNotFoundException;
import com.school.models.Role;
import com.school.models.Student;
import com.school.models.User;
import com.school.repositories.RoleRepository;
import com.school.repositories.StudentRepository;
import com.school.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private TeacherService teacherService;
    @Mock
    private StudentRepository studentRepository;
    @Autowired
    private RoleRepository roleRepository;
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl((TeacherServiceImpl) teacherService, studentRepository, roleRepository);
        roleRepository.save(new Role("STUDENT"));
    }

    @Test
    void tryCreateWithWrongInformation(){
        Student studentExist = new Student(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        studentRepository.save(studentExist);
        when(studentRepository.findByUserEmail(studentExist.getUser().getEmail())).thenReturn(Optional.of(studentExist));
        Student student = new Student(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        assertThrows(StudentExistException.class, () -> studentService.create(student));
    }

    @Test
    void createValidStudent() {
        //Given & When & Then
        studentService.create(new Student(new User("Userrrr", "Vadym", "Honcharuk", "User123")));
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
        assertThrows(StudentNotFoundException.class, () -> studentService.findById(-1L));
        }

    @Test
    void tryToUpdateWithException(){
        //given
        Student student = new Student(new User("Artem", "Moseichenko",  "am@gmil.com","Abubekir257"));
        student.setId(1);
        studentService.create(student);
        Student studentExist = new Student(new User("Artem", "Moseichenko",  "Newpassword@gmil.com","Abubekir257"));
        studentExist.setId(2);
        studentService.create(studentExist);
        //when
        when(studentRepository.findById(eq(1L))).thenReturn(Optional.of(student));
        //then
        Student updatedStudent = new Student(new User("rename", "surname",  "Newpassword@gmil.com", "Password441324"));
        updatedStudent.setId(1);
        when(studentRepository.findByUserEmail("Newpassword@gmil.com")).thenReturn(Optional.of(studentExist));
        assertThrowsExactly(StudentExistException.class, () -> {
            studentService.update(updatedStudent);
        });
    }
    @Test
    void tryToUpdateWithOutException(){
        //given
        Student student = new Student(new User("Artem", "Moseichenko",  "am@gmil.com","Abubekir257"));
        student.setId(1);
        studentService.create(student);
        //when
        when(studentRepository.findById(eq(1L))).thenReturn(Optional.of(student));
        Student updatedStudent = new Student(new User("rename", "surname",  "am@gmil.com", "Password441324"));
        updatedStudent.setId(1);
        when(studentRepository.findByUserEmail(student.getUser().getEmail())).thenReturn(Optional.of(student));
        studentService.update(updatedStudent);
        when(studentRepository.findById(eq(1L))).thenReturn(Optional.of(updatedStudent));
        //then
        Student res = studentService.findById(updatedStudent.getId());
        assertEquals(updatedStudent, res);
    }

    @Test
    void tryUpdate(){
        //given
        Student student = new Student(new User("Artem", "Moseichenko",  "am@gmil.com","Abubekir257"));
        student.setId(1);
        studentService.create(student);
        //then
        Student updatedStudent = new Student(new User("rename", "surname",  "Newpassword@gmil.com", "Password441324"));
        updatedStudent.setId(1);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(updatedStudent));
        studentService.update(updatedStudent);
        //when
        Student res = studentService.findById(1L);
        assertEquals(updatedStudent, res);
    }

    @Test
    void tryUpdateWithOurOwnEmail(){
        //given
        Student student = new Student(new User("Artem", "Moseichenko",  "am@gmil.com","Abubekir257"));
        student.setId(1);
        studentService.create(student);
        //then
        Student updatedStudent = new Student(new User("rename", "surname",  "am@gmil.com", "Password441324"));
        updatedStudent.setId(1);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        studentService.update(updatedStudent);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(updatedStudent));
        //when
        Student res = studentService.findById(1L);
        assertEquals(updatedStudent, res);
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
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteById(id));
    }

    @Test
    void findAllOrderedByName(){
        // Given
        List<Student> students = Arrays.asList(
                new Student(new User("Artem", "Moseichenko",  "am@gmil.com","Abubekir257")),
                new Student(new User("Body", "Roseichenko",  "am@gmil.com","Abubekir257")));
        when(studentRepository.findAll()).thenReturn(students);

        // When
        List<Student> result = studentService.findAllOrderedByName();

        // Then
        assertEquals(students, result);

    }

    @Test
    void findBySubjectName_Exists() {
        // Given
        String subjectName = "Mathematics";
        Student student = new Student();
        when(studentRepository.findStudentBySubjectsContains(eq(subjectName))).thenReturn(Optional.of(List.of(student)));

        // When
        List<Student> result = studentService.findBySubjectName(subjectName);

        // Then
        assertEquals(student, result.get(0));
    }

    @Test
    void findBySubjectName_NotExists() {
        // Given
        String subjectName = "Physics";
        when(studentRepository.findStudentBySubjectsContains(eq(subjectName))).thenReturn(Optional.empty());

        // When & Then
        assertThrows(StudentNotFoundException.class, () -> studentService.findBySubjectName(subjectName));
    }


    @Test
    void findByEmail_Exists() {
        // Given
        String email = "useruser@gmail.com";
        Student student = new Student();
        when(studentRepository.findByUserEmail(eq(email))).thenReturn(Optional.of(student));

        // When
        Student result = studentService.findByEmail(email);

        // Then
        assertEquals(student, result);
    }

    @Test
    void findByEmail_NotExists() {
        // Given
        String email = "notexisting@hhh.kkk";
        when(studentRepository.findByUserEmail(eq(email))).thenReturn(Optional.empty());

        // When & Then
        assertThrows(StudentNotFoundException.class, () -> studentService.findByEmail(email));
    }

}

