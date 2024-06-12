package com.school.dto.teacher;

import com.school.dto.subject.SubjectResponseWithTeacher;
import com.school.models.Teacher;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
public class TeacherResponseAll {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<SubjectResponseWithTeacher> subjects;

    public TeacherResponseAll(Teacher teacher){
        this.firstName = teacher.getUser().getFirstName();
        this.lastName = teacher.getUser().getLastName();
        this.email = teacher.getUser().getEmail();
        this.id = teacher.getId();
        this.subjects = teacher.getSubjects().stream()
                .map(SubjectResponseWithTeacher::new)
                .collect(Collectors.toList());

    }
}
