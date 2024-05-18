package com.school.dto;

import com.school.models.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class StudentRequest {
    private long id;
    private User user;

    public StudentRequest() {}

    public StudentRequest(long id, User user) {
        this.id = id;
        this.user = user;
    }
}