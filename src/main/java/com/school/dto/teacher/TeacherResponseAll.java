package com.school.dto.teacher;

import com.school.dto.subject.SubjectResponseWithTeacher;
import com.school.models.Teacher;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TeacherResponseAll {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private SubjectResponseWithTeacher[] subjects;

    public TeacherResponseAll(Teacher teacher){
        this.firstName = teacher.getUser().getFirstName();
        this.lastName = teacher.getUser().getLastName();
        this.email = teacher.getUser().getEmail();
        this.id = teacher.getId();
        this.subjects = new SubjectResponseWithTeacher[teacher.getSubjects().size()];
        for(int i = 0; i < subjects.length; i++){
            subjects[i] = new SubjectResponseWithTeacher(teacher.getSubjects().get(i));
        }
    }
}
