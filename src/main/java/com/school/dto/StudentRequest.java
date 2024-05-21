package com.school.dto;

import com.school.models.Student;
import com.school.models.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class StudentRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    public static Student toStudent(StudentRequest studentRequest){
        Student result = new Student(new User(studentRequest.getUsername(), studentRequest.getFirstName(), studentRequest.getLastName(), studentRequest.getPassword(), studentRequest.getEmail()));
        return result;
    }
}