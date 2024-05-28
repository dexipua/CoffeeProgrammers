package com.school.service;

import com.school.models.Role;

import java.util.List;

public interface RoleService {
    Role create(Role role);
    Role findById(long id);
    Role update(Role role);
    void delete(long id);
    List<Role> getAll();
    Role findByName(String name);
}
