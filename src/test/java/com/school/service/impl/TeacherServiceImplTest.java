package com.school.service.impl;

import com.school.dto.user.UserRequestCreate;
import com.school.dto.user.UserRequestUpdate;
import com.school.models.Role;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.models.User;
import com.school.repositories.TeacherRepository;
import com.school.service.RoleService;
import com.school.service.SubjectService;
import com.school.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private SubjectService subjectService;
    @Mock
    private RoleService roleService;
    @Mock
    private UserService userService;

    @InjectMocks
    private TeacherServiceImpl teacherService;
    private Role teacherRole;
    private Teacher teacher;
    private Teacher expected;
    private UserRequestCreate userRequestCreate;
    private UserRequestUpdate userRequestUpdate;
    @BeforeEach
    void setUp() {
        teacherRole = new Role("TEACHER");
        teacher = new Teacher(new User("Vlad", "Bulakovskyi", "email@gmail.com", "Vlad123"));
        teacher.getUser().setRole(teacherRole);
        teacher.setId(1);

        userRequestCreate = new UserRequestCreate();
        userRequestCreate.setFirstName("Vlad");
        userRequestCreate.setLastName("Bulakovskyi");
        userRequestCreate.setEmail("email@gmail.com");
        userRequestCreate.setPassword("Vlad123");

        expected = new Teacher(new User("NewVlad", "NewBulakovskyi", "email@gmail.com", "NewVlad123"));
        expected.getUser().setRole(teacherRole);
        expected.setId(1);

        userRequestUpdate = new UserRequestUpdate();
        userRequestUpdate.setFirstName("NewVlad");
        userRequestUpdate.setLastName("NewBulakovskyi");
        userRequestUpdate.setPassword("NewVlad123");
    }

    @Test
    void create() {
//      when
        when(roleService.findByName("TEACHER")).thenReturn(teacherRole);
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

//      then
        teacherService.create(userRequestCreate);
        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
        verify(teacherRepository).save(teacherArgumentCaptor.capture());
        Teacher actual = teacherArgumentCaptor.getValue();

        assertEquals("email@gmail.com", actual.getUser().getUsername());
        assertEquals("Vlad", actual.getUser().getFirstName());
        assertEquals("Bulakovskyi", actual.getUser().getLastName());
        assertEquals("Vlad123", actual.getUser().getPassword());
        assertEquals(teacherRole, actual.getUser().getRole());

        verify(roleService).findByName("TEACHER");
    }

    @Test
    void findById() {
        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        Teacher res = teacherService.findById(teacher.getId());
        assertThat(res).isEqualTo(teacher);
    }

    @Test
    void findAllByFirstNameAndLastName() {
        when(teacherRepository.findAllByUser_FirstNameContainingIgnoreCaseAndUser_LastNameContainingIgnoreCase("Vlad", "Bulakovskyi")).thenReturn(List.of(teacher));

        List<Teacher> result = teacherService.findAllByUser_FirstNameAndAndUser_LastName("Vlad", "Bulakovskyi");

        assertEquals(List.of(teacher), result);
        verify(teacherRepository, times(1)).findAllByUser_FirstNameContainingIgnoreCaseAndUser_LastNameContainingIgnoreCase("Vlad", "Bulakovskyi");
    }

    @Test
    void notFindById() {
        assertThrows(EntityNotFoundException.class, () -> teacherService.findById(-1));
    }

    @Test
    void tryToUpdate() {
        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        teacherService.update(teacher.getId(), userRequestUpdate);

        verify(teacherRepository, times(1)).findById(teacher.getId());
        verify(userService, times(1)).update(teacher.getUser().getId(), userRequestUpdate);
    }

    @Test
    void deleteWithSubjects() {
        Subject subject = new Subject();
        subject.setName("Math");
        subject.setTeacher(teacher);
        teacher.getSubjects().add(subject);
        teacherService.create(userRequestCreate);

        //when
        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        //then
        teacherService.delete(teacher.getId());
        assertNull(subject.getTeacher());
        verify(teacherRepository, times(1)).findById(teacher.getId());
        verify(teacherRepository, times(1)).delete(teacher);
    }

    @Test
    void deleteWithOutSubjects() {
        teacherService.create(userRequestCreate);

        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        teacherService.delete(teacher.getId());

        verify(teacherRepository).findById(teacher.getId());
        verify(teacherRepository).delete(teacher);
    }

    @Test
    void deleteChief() {
        teacherService.create(userRequestCreate);
        teacher.getUser().setRole(new Role("CHIEF_TEACHER"));
        //when
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        //then
        assertThrowsExactly(UnsupportedOperationException.class, () -> teacherService.delete(1L));
    }

    @Test
    void findAll() {
        teacherService.findAll();
        verify(teacherRepository).findAllByOrderByUser_LastNameAsc();
    }

    @Test
    void findBySubjectName() {
        Subject subject = new Subject();
        subject.setName("Math");
        teacher.getSubjects().add(subject);
        subject.setTeacher(teacher);
        teacherService.create(userRequestCreate);

        when(teacherRepository.findBySubjectNameIgnoreCase(subject.getName())).thenReturn(List.of(teacher));

        List<Teacher> result = teacherService.findBySubjectName(subject.getName());

        assertEquals(List.of(teacher), result);
    }

    @Test
    void notFindBySubjectName() {
        Subject subject = new Subject();
        subject.setName("Math");
        teacher.getSubjects().add(subject);
        subject.setTeacher(teacher);
        teacherService.create(userRequestCreate);

        assertEquals(List.of(), teacherService.findBySubjectName("Art"));
    }
}