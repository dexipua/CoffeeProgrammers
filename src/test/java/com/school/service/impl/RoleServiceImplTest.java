package com.school.service.impl;

import com.school.models.Role;
import com.school.models.User;
import com.school.repositories.RoleRepository;
import com.school.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    void create() {
        Role role = new Role("Dexip");
        roleService.create(role);

        ArgumentCaptor<Role> userArgumentCaptor = ArgumentCaptor.forClass(Role.class);

        verify(roleRepository).save(userArgumentCaptor.capture());

        Role actualRole = userArgumentCaptor.getValue();

        assertThat(actualRole).isEqualTo(role);
    }
    @Test
    void notCreate() {
        Role role = null;

        assertThrowsExactly(EntityNotFoundException.class, () -> {
            roleService.create(role);
        });
    }

    @Test
    void readById() {
        Role role = new Role("Dexip");
        roleService.create(role);

        when(roleRepository.findById(1l)).thenReturn(Optional.of(role));

        Role res = roleService.readById(1l);
        assertThat(res).isEqualTo(role);
    }

    @Test
    void notReadById() {
        Role role = new Role("Dexip");
        roleService.create(role);

        assertThrowsExactly(EntityNotFoundException.class, () -> {
            roleService.readById(2l);
        });
    }

    @Test
    void update() {
        Role role = new Role("Dexip");
        when(roleRepository.findById((long) role.getId())).thenReturn(Optional.of(role));
        when(roleRepository.save(role)).thenReturn(role);

        Role updatedRole = roleService.update(role);

        assertEquals(role, updatedRole);
        verify(roleRepository, times(1)).findById((long) role.getId());
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void notUpdate() {
        Role role = null;

        assertThrowsExactly(EntityNotFoundException.class, () -> {
            roleService.update(role);
        });
    }

    @Test
    void delete() {
        Role role = new Role("Some");
        roleService.create(role);

        when(roleRepository.findById((long) role.getId())).thenReturn(Optional.of(role));
        roleService.delete(role.getId());

        verify(roleRepository, times(1)).findById((long) role.getId());
        verify(roleRepository, times(1)).delete(role);
    }

    @Test
    void getAll() {
        roleService.getAll();

        verify(roleRepository).findAll();
    }

    @Test
    void findByName() {
        Role role = new Role("Dexip");
        roleService.create(role);

        when(roleRepository.findByName("Dexip")).thenReturn(Optional.of(role));

        Role res = roleService.findByName("Dexip");

        assertThat(res).isEqualTo(role);
    }

    @Test
    void notFindByName() {
        Role role = new Role("Dexip");
        roleService.create(role);

        assertThrowsExactly(EntityNotFoundException.class, () -> {
            Role res = roleService.findByName("Dexipbr");
        });

    }
}