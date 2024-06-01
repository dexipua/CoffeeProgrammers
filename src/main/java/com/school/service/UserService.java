package com.school.service;

import com.school.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User create(User user);
    User findById(long id);
    User update(User user);
    User findByEmail(String email);
}
