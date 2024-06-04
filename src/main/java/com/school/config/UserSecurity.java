package com.school.config;

import com.school.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {

    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final MarkService markService;

    private boolean checkUser(Authentication authentication, long userId) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String authenticatedUserEmail = user.getUsername();
        String ownEmail = userService.findById(userId).getEmail();
        return authenticatedUserEmail.equals(ownEmail);
    }
    public boolean checkUserByStudent(Authentication authentication, long userId) {
        return checkUser(authentication, studentService.findById(userId).getUser().getId());
    }
    public boolean checkUserByTeacher(Authentication authentication, long userId){
        return checkUser(authentication, teacherService.findById(userId).getUser().getId());
    }
    public boolean checkUserBySubject(Authentication authentication, long subjectId){
        return checkUser(authentication, subjectService.findById(subjectId).getTeacher().getUser().getId());
    }
    public boolean checkUserByMarkByStudent(Authentication authentication, long markId){
        return checkUser(authentication, markService.findById(markId).getStudent().getUser().getId());
    }
    public boolean checkUserByMarkByTeacher(Authentication authentication, long markId){
        return checkUser(authentication, markService.findById(markId).getSubject().getTeacher().getUser().getId());
    }
}
