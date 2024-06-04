package com.school.dto.student;

import com.school.models.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class StudentResponseSimple {
    private long id;
    private String firstName;
    private String lastName;

    public StudentResponseSimple(Student student) {
        this.id = student.getId();
        this.firstName = student.getUser().getFirstName();
        this.lastName = student.getUser().getLastName();
    }
}
