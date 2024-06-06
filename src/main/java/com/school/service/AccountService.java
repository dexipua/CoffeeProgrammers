package com.school.service;

import org.springframework.security.core.Authentication;

public interface AccountService {
    Object findAllInformationByRoleAndUserId(Authentication authentication);
}
