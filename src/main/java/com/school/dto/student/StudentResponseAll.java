package com.school.dto.student;

import com.school.dto.subject.SubjectResponseWithTeacher;
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
    private SubjectResponseWithTeacher[] subjects;

    public StudentResponseAll(Student student, double averageMark){
        this.id = student.getId();
        this.firstName = student.getUser().getFirstName();
        this.lastName = student.getUser().getLastName();
        this.email = student.getUser().getEmail();
        this.averageMark = Math.round(averageMark * 10.0) / 10.0;
        this.subjects = new SubjectResponseWithTeacher[student.getSubjects().size()];
        for(int i = 0; i < subjects.length; i++){
            subjects[i] = new SubjectResponseWithTeacher(student.getSubjects().get(i));
        }
    }
}