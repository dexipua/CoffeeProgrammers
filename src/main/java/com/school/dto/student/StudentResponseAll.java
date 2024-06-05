package com.school.dto.student;

import com.school.models.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class StudentResponseAll {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private double averageMark;
    private String[] subjects;

    public StudentResponseAll(Student student, double averageMark){
        this.id = student.getId();
        this.firstName = student.getUser().getFirstName();
        this.lastName = student.getUser().getLastName();
        this.email = student.getUser().getEmail();
        this.averageMark = Math.round(averageMark * 10.0) / 10.0;
        this.subjects = new String[student.getSubjects().size()];
        for(int i = 0; i < subjects.length; i++){
            subjects[i] = student.getSubjects().get(i).getName();
        }
    }
}