package com.school.service;

import com.school.dto.user.UserRequestUpdate;
import com.school.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User create(User user);
    User findById(long id);
    User update(long userToUpdateId, UserRequestUpdate userRequest);
    User findByEmail(String email);
}
