package com.school.service.impl;

import com.school.models.Role;
import com.school.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1);
        role.setName("ADMIN");
    }

    @Test
    void create() {
        when(roleRepository.save(role)).thenReturn(role);
        Role createdRole = roleService.create(role);
        assertEquals(role, createdRole);
        verify(roleRepository).save(role);
    }

    @Test
    void findById_WhenRoleExists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        Role foundRole = roleService.findById(1L);
        assertEquals(role, foundRole);
        verify(roleRepository).findById(1L);
    }

    @Test
    void findById_WhenRoleNotExists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> roleService.findById(1L));
        assertEquals("Role with id 1 not found", exception.getMessage());
        verify(roleRepository).findById(1L);
    }

    @Test
    void update_WhenRoleExists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleRepository.save(role)).thenReturn(role);
        Role updatedRole = roleService.update(role);
        assertEquals(role, updatedRole);
        verify(roleRepository).findById(1L);
        verify(roleRepository).save(role);
    }

    @Test
    void update_WhenRoleNotExists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> roleService.update(role));
        assertEquals("Role with id 1 not found", exception.getMessage());
        verify(roleRepository).findById(1L);
    }

    @Test
    void delete_WhenRoleExists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        roleService.delete(1L);
        verify(roleRepository).findById(1L);
        verify(roleRepository).delete(role);
    }

    @Test
    void delete_WhenRoleNotExists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> roleService.delete(1L));
        assertEquals("Role with id 1 not found", exception.getMessage());
        verify(roleRepository).findById(1L);
    }

    @Test
    void getAll() {
        List<Role> roles = Arrays.asList(role, new Role("USER"));
        when(roleRepository.findAll()).thenReturn(roles);
        List<Role> allRoles = roleService.getAll();
        assertEquals(roles, allRoles);
        verify(roleRepository).findAll();
    }

    @Test
    void findByName_WhenRoleExists() {
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(role));
        Role foundRole = roleService.findByName("ADMIN");
        assertEquals(role, foundRole);
        verify(roleRepository).findByName("ADMIN");
    }

    @Test
    void findByName_WhenRoleNotExists() {
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> roleService.findByName("ADMIN"));
        assertEquals("Role with name ADMIN not found", exception.getMessage());
        verify(roleRepository).findByName("ADMIN");
    }
}
