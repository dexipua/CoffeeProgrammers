package com.school.service.impl;

import com.school.models.Role;
import com.school.repositories.RoleRepository;
import com.school.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role create(Role role) {
        if (role != null) {
            return roleRepository.save(role);
        }
        throw new EntityNotFoundException("Role is null");
    }

    @Override
    public Role readById(long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role with id " + id + " not found"));
    }

    @Override
    public Role update(Role role) {
        if (role != null) {
            readById(role.getId());
            return roleRepository.save(role);
        }
        throw new EntityNotFoundException("Role is null");
    }

    @Override
    public void delete(long id) {
        Role role = readById(id);
        roleRepository.delete(role);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByName(String name) {
        if(roleRepository.findByName(name).isPresent()) {
            return roleRepository.findByName(name).get();
        }
        throw new EntityNotFoundException("Role with name " + name + " not found");
    }
}