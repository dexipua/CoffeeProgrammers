//package com.school.service.impl;
//
//import com.school.models.Role;
//import com.school.models.Student;
//import com.school.models.Subject;
//import com.school.models.Teacher;
//import com.school.models.User;
//import com.school.repositories.StudentRepository;
//import com.school.service.RoleService;
//import com.school.service.TeacherService;
//import jakarta.persistence.EntityExistsException;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class StudentServiceImplTest {
//
//    @Mock
//    private TeacherService teacherService;
//
//    @Mock
//    private StudentRepository studentRepository;
//
//    @Mock
//    private RoleService roleService;
//
//    @InjectMocks
//    private StudentServiceImpl studentService;
//
//    private Student student;
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        user = new User("FirstName", "LastName", "email@gm.com", "Password123");
//        student = new Student(user);
//        student.setId(1L);
//    }
//
//    @Test
//    void create_WhenStudentDoesNotExist() {
//        when(studentRepository.findByUserEmail(user.getEmail())).thenReturn(Optional.empty());
//        when(roleService.findByName("STUDENT")).thenReturn(new Role("STUDENT"));
//        when(studentRepository.save(student)).thenReturn(student);
//
//        Student createdStudent = studentService.create(student);
//
//        assertEquals(student, createdStudent);
//        verify(studentRepository).findByUserEmail(user.getEmail());
//        verify(roleService).findByName("STUDENT");
//        verify(studentRepository).save(student);
//    }
//
//    @Test
//    void create_WhenStudentExists() {
//        when(studentRepository.findByUserEmail(user.getEmail())).thenReturn(Optional.of(student));
//
//        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> studentService.create(student));
//
//        assertEquals("Student with such email already exist", exception.getMessage());
//        verify(studentRepository).findByUserEmail(user.getEmail());
//        verify(studentRepository, never()).save(student);
//    }
//
//    @Test
//    void findById_WhenStudentExists() {
//        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
//
//        Student foundStudent = studentService.findById(1L);
//
//        assertEquals(student, foundStudent);
//        verify(studentRepository).findById(1L);
//    }
//
//    @Test
//    void findById__WhenStudentNotExists() {
//        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
//
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> studentService.findById(1L));
//
//        assertEquals("Student with id 1 not found", exception.getMessage());
//        verify(studentRepository).findById(1L);
//    }
//
//    @Test
//    void update_WhenStudentExists() {
//        Student updatedStudent = new Student(new User("NewFirstName", "NewLastName", "newemail@example.com", "NewPassword123"));
//        updatedStudent.setId(1L);
//
//        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
//        when(studentRepository.findByUserEmail("newemail@example.com")).thenReturn(Optional.empty());
//        when(roleService.findByName("STUDENT")).thenReturn(new Role("STUDENT"));
//        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);
//
//        Student result = studentService.update(updatedStudent);
//
//        assertEquals(updatedStudent, result);
//        verify(studentRepository).findById(1L);
//        verify(studentRepository).findByUserEmail("newemail@example.com");
//        verify(roleService).findByName("STUDENT");
//        verify(studentRepository).save(updatedStudent);
//    }
//
//    @Test
//    void deleteById_WhenStudentExists() {
//        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
//
//        studentService.deleteById(1L);
//
//        verify(studentRepository).findById(1L);
//        verify(studentRepository).delete(student);
//    }
//
//    @Test
//    void deleteById_WhenStudentNotExists() {
//        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
//
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> studentService.deleteById(1L));
//
//        assertEquals("Student with id 1 not found", exception.getMessage());
//        verify(studentRepository).findById(1L);
//        verify(studentRepository, never()).delete(student);
//    }
//
//    @Test
//    void findAllOrderedByName_ShouldReturnOrderedStudents() {
//        List<Student> students = Arrays.asList(
//                new Student(new User("email1@gm.com", "Pavlo", "Last", "password")),
//                new Student(new User("email2@gm.com", "Alice", "First", "password"))
//        );
//        when(studentRepository.findAllByOrderByUser()).thenReturn(Optional.of(students));
//
//        List<Student> result = studentService.findAllOrderedByName();
//
//        assertEquals(students, result);
//        verify(studentRepository).findAllByOrderByUser();
//    }
//
//    @Test
//    void findBySubjectName_WhenSubjectExists() {
//        List<Student> students = Arrays.asList(student);
//        when(studentRepository.findStudentBySubjectName("Mathematics")).thenReturn(Optional.of(students));
//
//        List<Student> result = studentService.findBySubjectName("Mathematics");
//
//        assertEquals(students, result);
//        verify(studentRepository).findStudentBySubjectName("Mathematics");
//    }
//
//    @Test
//    void findBySubjectName_WhenSubjectNotExists() {
//        when(studentRepository.findStudentBySubjectName("Mathematics")).thenReturn(Optional.empty());
//
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> studentService.findBySubjectName("Mathematics"));
//
//        assertEquals("Student with Mathematics not found", exception.getMessage());
//        verify(studentRepository).findStudentBySubjectName("Mathematics");
//    }
//
//    @Test
//    void findByEmail_WhenStudentExists() {
//        when(studentRepository.findByUserEmail("email@gm.com")).thenReturn(Optional.of(student));
//
//        Student result = studentService.findByEmail("email@gm.com");
//
//        assertEquals(student, result);
//        verify(studentRepository).findByUserEmail("email@gm.com");
//    }
//
//    @Test
//    void findByEmail_WhenStudentNotExists() {
//        when(studentRepository.findByUserEmail("email@gm.com")).thenReturn(Optional.empty());
//
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> studentService.findByEmail("email@gm.com"));
//
//        assertEquals("Student with email@gm.com not found", exception.getMessage());
//        verify(studentRepository).findByUserEmail("email@gm.com");
//    }
//
//    @Test
//    void findStudentsByTeacherId_WhenTeacherExists() {
//        Subject subject = new Subject();
//        subject.setStudents(List.of(student));
//        Teacher teacher = new Teacher();
//        teacher.setSubjects(List.of(subject));
//
//        when(teacherService.findById(1L)).thenReturn(teacher);
//
//        List<Student> result = studentService.findStudentsByTeacherId(1L);
//
//        assertEquals(List.of(student), result);
//        verify(teacherService).findById(1L);
//    }
//
//    @Test
//    void findStudentsByTeacherId_WhenTeacherHasNoStudents() {
//        Teacher teacher = new Teacher();
//        teacher.setSubjects(new ArrayList<>());
//
//        when(teacherService.findById(1L)).thenReturn(teacher);
//
//        List<Student> result = studentService.findStudentsByTeacherId(1L);
//
//        assertTrue(result.isEmpty());
//        verify(teacherService).findById(1L);
//    }
//}
