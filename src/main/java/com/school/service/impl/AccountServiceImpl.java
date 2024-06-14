package com.school.service.impl;

import com.school.models.Mark;
import com.school.models.Subject;
import com.school.models.User;
import com.school.service.AccountService;
import com.school.service.MarkService;
import com.school.service.StudentService;
import com.school.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final MarkService markService;
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


    @Override
    public HashMap<Subject, List<Mark>> findBookmark() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long studentId = studentService.findByUserId(user.getId()).getId();
        return markService.findAllByStudentId(studentId);

    }


}
