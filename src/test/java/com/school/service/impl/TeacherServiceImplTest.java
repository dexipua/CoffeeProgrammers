package com.school.service.impl;

import com.school.exception.TeacherExistException;
import com.school.exception.TeacherNotFoundException;
import com.school.exception.UserExistsException;
import com.school.models.*;
import com.school.repositories.RoleRepository;
import com.school.repositories.SubjectRepository;
import com.school.repositories.TeacherRepository;
import com.school.service.RoleService;
import com.school.service.SubjectService;
import com.school.service.TeacherService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private SubjectService subjectService;
    @Mock
    private RoleService roleService;
    @InjectMocks
    private TeacherServiceImpl teacherService;

    @BeforeEach
    void setUp() {
        roleService.create(new Role("TEACHER"));
    }

    @Test
    void create() {
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        teacherService.create(teacher);
        //when
        ArgumentCaptor<Teacher> teacherArgumentCaptor = ArgumentCaptor.forClass(Teacher.class);
        verify(teacherRepository).save(teacherArgumentCaptor.capture());
        //then
        Teacher actualTeacher = teacherArgumentCaptor.getValue();
        assertThat(actualTeacher).isEqualTo(teacher);
    }

    @Test
    void tryCreateWithWrongInformation() {
        Teacher teacherExist = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        teacherRepository.save(teacherExist);
        when(teacherRepository.findByUserEmail(teacherExist.getUser().getEmail())).thenReturn(Optional.of(teacherExist));
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        assertThrows(TeacherExistException.class, () -> teacherService.create(teacher));
    }

    @Test
    void findById() {
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        teacherService.create(teacher);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        Teacher res = teacherService.findById(1L);
        assertThat(res).isEqualTo(teacher);
    }

    @Test
    void notFindById() {
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        teacherService.create(teacher);

        assertThrows(TeacherNotFoundException.class, () -> teacherService.findById(2));
    }

    @Test
    void tryToUpdateWithException() {
        //given
        Teacher teacher = new Teacher(new User("Artem", "Moseichenko", "am@gmil.com", "Abubekir257"));
        teacher.setId(1);
        teacherService.create(teacher);
        Teacher teacherExist = new Teacher(new User("Artem", "Moseichenko", "Newpassword@gmil.com", "Abubekir257"));
        teacher.setId(2);
        teacherService.create(teacherExist);
        //when
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.findByUserEmail(teacherExist.getUser().getEmail())).thenReturn(Optional.of(teacherExist));
        //then
        Teacher updatedTeacher = new Teacher(new User("rename", "surname", "Newpassword@gmil.com", "Password441324"));
        updatedTeacher.setId(1);
        assertThrowsExactly(TeacherExistException.class, () -> {
            teacherService.update(updatedTeacher);
        });
    }

    @Test
    void tryUpdateWithOurOwnEmail() {
        //given
        Teacher teacher = new Teacher(new User("Artem", "Moseichenko", "am@gmil.com", "Abubekir257"));
        teacher.setId(1);
        teacherService.create(teacher);
        //when
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.findByUserEmail(teacher.getUser().getEmail())).thenReturn(Optional.of(teacher));
        //then
        Teacher updatedTeacher = new Teacher(new User("rename", "surname", "am@gmil.com", "Password441324"));
        updatedTeacher.setId(1);
        teacherService.update(updatedTeacher);
        //when
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(updatedTeacher));
        Teacher res = teacherService.findById(1L);
        assertEquals(updatedTeacher, res);
    }

    @Test
    void tryUpdate() {
        //given
        Teacher teacher = new Teacher(new User("Artem", "Moseichenko", "am@gmil.com", "Abubekir257"));
        teacher.setId(1);
        teacherService.create(teacher);
        //then
        Teacher updatedTeacher = new Teacher(new User("rename", "surname", "Newpassword@gmil.com", "Password441324"));
        updatedTeacher.setId(1);
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(updatedTeacher));
        teacherService.update(updatedTeacher);
        //when
        Teacher res = teacherService.findById(1L);
        assertEquals(updatedTeacher, res);
    }

    @Test
    void deleteWithSubjects() {
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        teacherService.create(teacher);
        //when
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(subjectService.findByTeacher_Id(1L));
        //then
        teacherService.delete(1L);

        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).delete(teacher);
    }

    @Test
    void deleteWithOutSubjects() {
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        teacherService.create(teacher);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        teacherService.delete(1L);

        verify(teacherRepository).findById(1L);
        verify(teacherRepository).delete(teacher);
    }

    @Test
    void deleteChief() {
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        teacherService.create(teacher);
        teacher.getUser().setRole(new Role("CHIEF_TEACHER"));
        //when
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        //then
        assertThrowsExactly(TeacherExistException.class, () -> teacherService.delete(1L));
    }

    @Test
    void findAll() {
        teacherService.findAllByOrderByUser();

        verify(teacherRepository).findAllByOrderByUser();
    }

    @Test
    void findBySubjectName() {
        Subject subject = new Subject("Mathematics");
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        teacher.addSubject(subject);
        subject.setTeacher(teacher);
        teacherService.create(teacher);

        when(teacherRepository.findBySubjectsContains(subject.getName())).thenReturn(Optional.of(teacher));

        Teacher result = teacherService.findBySubjectName(subject.getName());

        assertEquals(teacher, result);
    }

    @Test
    void notFindBySubjectName() {
        Subject subject = new Subject("Mathematics");
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        teacher.addSubject(subject);
        subject.setTeacher(teacher);
        teacherService.create(teacher);

        assertThrows(TeacherNotFoundException.class, () -> teacherService.findBySubjectName("Art"));
    }

    @Test
    void findByEmail() {
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "some@gmail.com", "Vlad123"));
        teacherService.create(teacher);
        when(teacherRepository.findByUserEmail(teacher.getUser().getEmail())).thenReturn(Optional.of(teacher));

        Teacher result = teacherService.findByEmail(teacher.getUser().getEmail());

        assertEquals(teacher, result);
    }

    @Test
    void notFindByEmail() {
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        teacherService.create(teacher);

        assertThrows(TeacherNotFoundException.class, () -> teacherService.findByEmail("dssdssdssd"));
    }
}