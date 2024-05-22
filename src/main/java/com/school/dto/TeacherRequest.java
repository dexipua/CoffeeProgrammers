package com.school.dto;

import com.school.models.Teacher;
import com.school.models.User;
import lombok.Data;

@Data
public class TeacherRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    public static Teacher toTeacher(TeacherRequest teacherRequest){
        Teacher result = new Teacher(new User(teacherRequest.getUsername(), teacherRequest.getFirstName(), teacherRequest.getLastName(), teacherRequest.getPassword(), teacherRequest.getEmail()));
        return result;
    }
}