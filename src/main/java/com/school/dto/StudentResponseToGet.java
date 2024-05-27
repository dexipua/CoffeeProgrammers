package com.school.dto;

import com.school.models.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class StudentResponseToGet {
    private String firstName;
    private String lastName;
    private String email;
    private long id;

    public StudentResponseToGet(Student student) {
        this.firstName = student.getUser().getFirstName();
        this.lastName = student.getUser().getLastName();
        this.email = student.getUser().getEmail();
        this.id = student.getUser().getId();
    }
}
