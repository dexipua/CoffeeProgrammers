package com.school.service.impl;

import com.school.dto.student.StudentResponseAll;
import com.school.dto.teacher.TeacherResponseAll;
import com.school.models.User;
import com.school.service.AccountService;
import com.school.service.MarkService;
import com.school.service.StudentService;
import com.school.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final MarkService markService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    @Override
    public Object findAllInformationByRoleAndUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getRole().getName().equals("STUDENT")){
            return new StudentResponseAll(studentService.findByUserId(user.getId()),
                    markService.findAverageByStudentId(studentService.findByUserId(user.getId()).getId()));
        }else{
            return new TeacherResponseAll(teacherService.findByUserId(user.getId()));
        }
    }
}
