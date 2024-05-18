package com.school.dto;

import com.school.models.Teacher;
import lombok.Data;

import java.util.Arrays;

@Data
public class TeacherResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private long id;
    private String[] subjects;

    public TeacherResponse(Teacher teacher){
        this.username = teacher.getUser().getUsername();
        this.firstName = teacher.getUser().getFirstName();
        this.lastName = teacher.getUser().getLastName();
        this.email = teacher.getUser().getEmail();
        this.id = teacher.getId();
        this.subjects = new String[teacher.getSubjects().size()];
        for(int i = 0; i < subjects.length; i++){
            subjects[i] = teacher.getSubjects().get(i).getName();
        }
    }
}
