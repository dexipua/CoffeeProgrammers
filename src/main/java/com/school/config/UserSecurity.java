package com.school.config;

import com.school.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {


    private final UserService userService;

    public boolean checkUserId(Authentication authentication, long userId) {
        String authenticatedUserEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        return authenticatedUserEmail.equals(userService.readById(userId).getEmail());
    }
}
