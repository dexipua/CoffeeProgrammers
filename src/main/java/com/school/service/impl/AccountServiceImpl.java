package com.school.service.impl;

import com.school.models.User;
import com.school.service.AccountService;
import com.school.service.StudentService;
import com.school.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final StudentService studentService;
    private final TeacherService teacherService;

    @Override
    public long findRoleIdByRoleAndUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        if (user.getRole().getName().equals("STUDENT")) {
            return studentService.findByUserId(userId).getId();
        } else {
            return teacherService.findByUserId(userId).getId();
        }
    }

}
