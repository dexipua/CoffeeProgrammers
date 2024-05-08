package com.school.repository;

import com.school.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void itShouldFindByName() {
        //given
        Role role = new Role("teacher");
        roleRepository.save(role);

        //when
        Role result = roleRepository.findByName("teacher").get();
        boolean res = result.equals(role);

        //then
        assertThat(res).isTrue();
    }
}