package com.school.service.impl;

import com.school.models.Role;
import com.school.repositories.RoleRepository;
import com.school.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role create(@NotNull Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findById(long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role with id " + id + " not found"));
    }

    @Override
    public Role update(@NotNull Role role) {
        findById(role.getId());
        return roleRepository.save(role);
    }

    @Override
    public void delete(long id) {
        Role role = findById(id);
        roleRepository.delete(role);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByName(@NotNull String name) {
        return roleRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Role with name " + name + " not found"));
    }
}