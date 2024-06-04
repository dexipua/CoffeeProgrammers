package com.school.dto.teacher;

import com.school.models.Teacher;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TeacherResponseSimple {
    private long id;
    private String firstName;
    private String lastName;

    public TeacherResponseSimple(Teacher teacher){
        this.firstName = teacher.getUser().getFirstName();
        this.lastName = teacher.getUser().getLastName();
        this.id = teacher.getId();
    }
}
