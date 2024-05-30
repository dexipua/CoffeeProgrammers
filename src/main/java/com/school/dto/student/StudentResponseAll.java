package com.school.dto.student;

import com.school.models.Student;
import lombok.*;

@Setter
@Getter
@Data
public class StudentResponseAll {
    private String firstName;
    private String lastName;
    private String email;
    private long id;
    private String[] subjects;

    public StudentResponseAll(Student student){
        this.firstName = student.getUser().getFirstName();
        this.lastName = student.getUser().getLastName();
        this.email = student.getUser().getEmail();
        this.id = student.getId();
        this.subjects = new String[student.getSubjects().size()];
        for(int i = 0; i < subjects.length; i++){
            subjects[i] = student.getSubjects().get(i).getName();
        }
    }
}