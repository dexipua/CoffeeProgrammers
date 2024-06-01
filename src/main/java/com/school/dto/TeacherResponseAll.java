package com.school.dto;

import com.school.models.Teacher;
import jakarta.validation.constraints.NotNull;
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
    private String[] subjects;

    public TeacherResponseAll(@NotNull Teacher teacher){
        this.firstName = teacher.getUser().getFirstName();
        this.lastName = teacher.getUser().getLastName();
        this.email = teacher.getUser().getEmail();
        this.id = teacher.getId();
        this.subjects = new String[teacher.getSubjects().size()];
        for(int i = 0; i < subjects.length; i++){
            subjects[i] = teacher.getSubjects().get(i).getName();
        }
    }
}
