package com.school.dto;

import com.school.models.Teacher;
import com.school.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class TeacherRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public static Teacher toTeacher(TeacherRequest teacherRequest){
        return new Teacher(new User(teacherRequest.getFirstName(), teacherRequest.getLastName(), teacherRequest.getEmail(), teacherRequest.getPassword()));
    }
}