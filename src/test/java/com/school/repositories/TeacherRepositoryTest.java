package com.school.repositories;

import com.school.models.Teacher;
import com.school.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @AfterEach
    void tearDown() {
        teacherRepository.deleteAll();
    }

    @Test
    void findByUserId() {
        //given
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherRepository.save(teacher);

        //when
        Teacher res = teacherRepository.findByUserId(teacher.getUser().getId()).get();
        boolean result = res.equals(teacher);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void notFindByUserId() {
        //given
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherRepository.save(teacher);

        //when
        Optional<Teacher> res = teacherRepository.findByUserId(-1);
        assertThrows(NoSuchElementException.class, () -> {
            Teacher value = res.get();
        });
    }

    @Test
    void findByEmail() {
        //given
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherRepository.save(teacher);

        //when
        Teacher res = teacherRepository.findByEmail("vladobrod@gmail.com").get();
        boolean result = res.equals(teacher);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void notFindByEmail() {
        //given
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherRepository.save(teacher);

        //then
        Optional<Teacher> res = teacherRepository.findByEmail("dssdssdssd");
        assertThrows(NoSuchElementException.class, () -> {
            Teacher value = res.get();
        });

    }

    @Test
    void findByUsername() {
        //given
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherRepository.save(teacher);

        //when
        Teacher res = teacherRepository.findByUsername("Vladobrod").get();
        boolean result = res.equals(teacher);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void notFindByUserName() {
        //given
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123", "vladobrod@gmail.com"));
        teacherRepository.save(teacher);

        //then
        Optional<Teacher> res = teacherRepository.findByUsername("dssdssdssd");
        assertThrows(NoSuchElementException.class, () -> {
            Teacher value = res.get();
        });

    }
}
