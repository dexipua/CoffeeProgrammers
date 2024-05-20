package com.school.dto;

import com.school.models.Student;
import com.school.models.Subject;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private long id;
    private List<Subject> subjects;

    public StudentResponse(Student student){
        this.username = student.getUser().getUsername();
        this.firstName = student.getUser().getFirstName();
        this.lastName = student.getUser().getLastName();
        this.email = student.getUser().getEmail();
        this.id = student.getId();
        this.subjects = student.getSubjects();
    }
}