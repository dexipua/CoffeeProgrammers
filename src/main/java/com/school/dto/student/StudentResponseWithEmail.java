package com.school.dto.student;

import com.school.models.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class StudentResponseWithEmail {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public StudentResponseWithEmail(Student student){
        this.id = student.getId();
        this.firstName = student.getUser().getFirstName();
        this.lastName = student.getUser().getLastName();
        this.email = student.getUser().getEmail();
    }
}