package com.school.service.impl;

import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.models.User;
import com.school.repositories.RoleRepository;
import com.school.repositories.TeacherRepository;
import com.school.service.RoleService;
import com.school.service.TeacherService;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        teacherService = new TeacherServiceImpl(teacherRepository);
    }

    @AfterEach
    void tearDown() {
        teacherRepository.deleteAll();
    }

    @Test
    void create(){
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        assertThatNoException()
            //when
            .isThrownBy(() -> teacherService.create(teacher));
    }

    @Test
    void notCreate(){
        Teacher teacher = null;
        assertThrows(EntityNotFoundException.class, () -> teacherService.create(teacher));
    }

    @Test
    void findById() {
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherService.create(teacher);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        Teacher res = teacherService.findById(1L);
        assertThat(res).isEqualTo(teacher);
    }

    @Test
    void notFindById() {
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherService.create(teacher);

        assertThrows(EntityNotFoundException.class, () -> teacherService.findById(2));
    }

    @Test
    void update(){
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher updatedTeacher = teacherService.update(teacher);

        assertEquals(teacher, updatedTeacher);
        verify(teacherRepository, times(1)).findById(teacher.getId());
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void notUpdate(){
        Teacher teacher = null;

        assertThrowsExactly(EntityNotFoundException.class, () -> teacherService.update(teacher));
    }

    @Test
    void delete(){
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherService.create(teacher);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        teacherService.delete(1L);

        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).delete(teacher);
    }

    @Test
    void findAll(){
        teacherService.findAll();

        verify(teacherRepository).findAll();
    }

    @Test
    void findBySubjectName(){
        Subject subject = new Subject("Mathematics");
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacher.addSubject(subject);
        subject.setTeacher(teacher);
        teacherService.create(teacher);

        when(teacherRepository.findBySubjectName(subject.getName())).thenReturn(Optional.of(teacher));

        Teacher result = teacherService.findBySubjectName(subject.getName());

        assertEquals(teacher, result);
    }

    @Test
    void notFindBySubjectName(){
        Subject subject = new Subject("Mathematics");
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacher.addSubject(subject);
        subject.setTeacher(teacher);
        teacherService.create(teacher);

        assertThrows(EntityNotFoundException.class, () -> teacherService.findBySubjectName("Art"));
    }

    @Test
    void findByUsername(){
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherService.create(teacher);
        when(teacherRepository.findByUsername(teacher.getUser().getUsername())).thenReturn(Optional.of(teacher));

        Teacher result = teacherService.findByUsername(teacher.getUser().getUsername());

        assertEquals(teacher, result);
    }

    @Test
    void notFindByUsername(){
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherService.create(teacher);

        assertThrows(EntityNotFoundException.class, () -> teacherService.findByUsername("dssdssdssd"));
    }

    @Test
    void findByEmail(){
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherService.create(teacher);
        when(teacherRepository.findByEmail(teacher.getUser().getEmail())).thenReturn(Optional.of(teacher));

        Teacher result = teacherService.findByEmail(teacher.getUser().getEmail());

        assertEquals(teacher, result);
    }

    @Test
    void notFindByEmail(){
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherService.create(teacher);

        assertThrows(EntityNotFoundException.class, () -> teacherService.findByEmail("dssdssdssd"));
    }
}
