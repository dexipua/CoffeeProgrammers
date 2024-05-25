package com.school.dto;

import com.school.models.Teacher;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TeacherResponseToGet {
    private String firstName;
    private String lastName;
    private String email;
    private long id;

    public TeacherResponseToGet(Teacher teacher){
        this.firstName = teacher.getUser().getFirstName();
        this.lastName = teacher.getUser().getLastName();
        this.email = teacher.getUser().getEmail();
        this.id = teacher.getId();
    }
}
