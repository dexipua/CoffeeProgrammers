package com.school.service;

import com.school.models.Role;

public interface RoleService {
    Role findByName(String name);
}
