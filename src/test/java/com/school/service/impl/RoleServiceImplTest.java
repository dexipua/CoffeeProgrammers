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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
