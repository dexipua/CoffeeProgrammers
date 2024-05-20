package com.school.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.school.models.Student;
import com.school.models.Teacher;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class SubjectRequest {
    String name;
    Teacher teacher; //TODO
    List<Student> students;

    @JsonCreator
    public SubjectRequest(
            @JsonProperty("name") @NotNull @NotEmpty String name,
            @JsonProperty("teacher") Teacher teacher,
            @JsonProperty("student_list") List<Student> students
    ) {
        this.name = name;
        this.teacher = teacher;
        this.students = students;
    }
}
