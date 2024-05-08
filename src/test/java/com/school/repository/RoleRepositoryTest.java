package com.school.repository;

import com.school.models.Role;
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
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void FindByName() {
        //given
        Role role = new Role("teacher");
        roleRepository.save(role);

        //when
        Role result = roleRepository.findByName("teacher").get();
        boolean res = result.equals(role);

        //then
        assertThat(res).isTrue();
    }
    @Test
    void NotFindByName() {
        //given
        Role role = new Role("teacher");
        roleRepository.save(role);

        //then
        Optional<Role> res = roleRepository.findByName("teacrgrher");
        assertThrows(NoSuchElementException.class, () -> {
            Role value = res.get();
        });
    }
}