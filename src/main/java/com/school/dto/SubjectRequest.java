package com.school.dto;

import lombok.Data;

@Data
public class SubjectRequest {
    String name;
    Long teacherId;
    Long[] studentsId;
}
