package com.school.service.impl;

import com.school.dto.user.UserRequestCreate;
import com.school.dto.user.UserRequestUpdate;
import com.school.models.*;
import com.school.repositories.StudentRepository;
import com.school.service.RoleService;
import com.school.service.TeacherService;
import com.school.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private Student expected;
    private User user;

    private Subject subject;

    private Role studentRole = new Role("STUDENT");

    private UserRequestCreate userRequestCreate = new UserRequestCreate();
    private UserRequestUpdate userRequestUpdate = new UserRequestUpdate();

    @BeforeEach
    void setUp() {
        user = new User("FirstName", "LastName", "email@gm.com", "Password123");
        user.setId(1);

        subject = new Subject();
        subject.setName("Math");
        subject.setId(1);

        student = new Student(user);
        userRequestCreate.setFirstName("FirstName");
        userRequestCreate.setLastName("LastName");
        userRequestCreate.setEmail("email@gm.com");
        userRequestCreate.setPassword("Password123");
        student.setId(1);
        user.setRole(studentRole);
        userRequestUpdate.setFirstName("NewFirstName");
        userRequestUpdate.setLastName("NewLastName");
        userRequestUpdate.setPassword("NewPassword123");
        expected = new Student(new User("NewFirstName", "NewLastName", "email@gm.com","NewPassword123"));
        expected.setId(1);
        expected.getUser().setRole(studentRole);
    }

    @Test
    void findByUserId() {
        when(studentRepository.findByUserId(student.getUser().getId())).thenReturn(Optional.of(student));

        Student res = studentService.findByUserId(student.getUser().getId());
        verify(studentRepository, times(1)).findByUserId(student.getUser().getId());
        assertThat(res).isEqualTo(student);
    }

    @Test
    void notFindByUserId() {
        assertThrowsExactly(EntityNotFoundException.class, () -> studentService.findByUserId(-1));
    }

    @Test
    void getStudentsCount() {
        when(studentRepository.findAll()).thenReturn(List.of(student, new Student()));

        Long res = studentService.getStudentsCount();
        verify(studentRepository, times(1)).findAll();
        assertThat(res).isEqualTo(2);
    }

    @Test
    void findAllBySubjectIdIsNot() {
        // Mock the repository response
        student.setSubjects(Set.of(subject));
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student, expected));

        // Call the service method
        List<Student> result = studentService.findAllBySubjectsIdIsNot(subject.getId());

        // Verify the results
        assertTrue(result.contains(expected));
    }

    @Test
    void create_WhenStudentDoesNotExist() {
        when(roleService.findByName("STUDENT")).thenReturn(studentRole);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        //then
        studentService.create(userRequestCreate);
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student actual = studentArgumentCaptor.getValue();

        assertEquals("email@gm.com", actual.getUser().getUsername());
        assertEquals("FirstName", actual.getUser().getFirstName());
        assertEquals("LastName", actual.getUser().getLastName());
        assertEquals("Password123", actual.getUser().getPassword());
        assertEquals(studentRole, actual.getUser().getRole());

        verify(roleService).findByName("STUDENT");
    }


    @Test
    void findById_WhenStudentExists() {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        Student foundStudent = studentService.findById(student.getId());

        assertEquals(student, foundStudent);
        verify(studentRepository).findById(student.getId());
    }

    @Test
    void findById_WhenStudentNotExists() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> studentService.findById(1L));

        assertEquals("Student with id 1 not found", exception.getMessage());
        verify(studentRepository).findById(1L);
    }

    @Test
    void update_WhenStudentExists() {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        studentService.update(student.getId(), userRequestUpdate);

        verify(studentRepository, times(1)).findById(student.getId());
        verify(userService, times(1)).update(student.getUser().getId(), userRequestUpdate);
    }

    @Test
    void deleteById_WhenStudentExists() {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        studentService.deleteById(student.getId());

        verify(studentRepository).findById(student.getId());
        verify(studentRepository).delete(student);
    }

    @Test
    void deleteById_WhenStudentNotExists() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> studentService.deleteById(1L));

        assertEquals("Student with id 1 not found", exception.getMessage());
        verify(studentRepository).findById(1L);
        verify(studentRepository, never()).delete(student);
    }

    @Test
    void findAllOrderedByName_ShouldReturnOrderedStudents() {
        List<Student> students = Arrays.asList(
                student,
                expected
        );
        when(studentRepository.findAllByOrderByUser_LastNameAsc()).thenReturn(List.of(student, expected));

        List<Student> result = studentService.findAllOrderedByName();

        assertEquals(students, result);
        verify(studentRepository).findAllByOrderByUser_LastNameAsc();
    }

    @Test
    void findStudentsByTeacherId_WhenTeacherExists() {
        Subject subject = new Subject();
        subject.setStudents(Set.of(student));
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setSubjects(Set.of(subject));

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);
        when(studentRepository.findAllByTeacherId(teacher.getId())).thenReturn(List.of(student));

        List<Student> result = studentService.findStudentsByTeacherId(teacher.getId());

        assertEquals(List.of(student), result);
        verify(teacherService).findById(teacher.getId());
    }

    @Test
    void findStudentsByTeacherId_WhenTeacherHasNoStudents() {
        Teacher teacher = new Teacher();
        teacher.setId(1);

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);

        List<Student> result = studentService.findStudentsByTeacherId(teacher.getId());

        assertTrue(result.isEmpty());
        verify(teacherService).findById(teacher.getId());
    }

    @Test
    void findAllByUser_FirstNameAndUser_LastName() {
        when(studentRepository.findAllByUser_FirstNameContainingIgnoreCaseAndUser_LastNameContainingIgnoreCase("FirstName", "LastName")).thenReturn(List.of(student));

        List<Student> actual = studentService.findAllByUser_FirstNameAndUser_LastName("FirstName", "LastName");

        assertEquals(List.of(student), actual);
        verify(studentRepository, times(1)).findAllByUser_FirstNameContainingIgnoreCaseAndUser_LastNameContainingIgnoreCase("FirstName", "LastName");
    }
}
