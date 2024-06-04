package com.school.dto.student;

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
    private long id;

    public StudentResponseToGet(Student student) {
        this.firstName = student.getUser().getFirstName();
        this.lastName = student.getUser().getLastName();
        this.id = student.getId();
    }
}
