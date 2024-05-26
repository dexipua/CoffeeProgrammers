package com.school.config;

import com.school.service.StudentService;
import com.school.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {

    private final StudentService studentService;
    private final TeacherService teacherService;

    public boolean checkUserByStudent(Authentication authentication, long userId) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String authenticatedUserEmail = user.getUsername();
        String ownEmail = studentService.findById(userId).getUser().getUsername();
        return authenticatedUserEmail.equals(ownEmail);
    }
    public boolean checkUserByTeacher(Authentication authentication, long userId){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String authenticatedUserEmail = user.getUsername();
        String ownEmail = teacherService.findById(userId).getUser().getUsername();
        return authenticatedUserEmail.equals(ownEmail);
    }
}
