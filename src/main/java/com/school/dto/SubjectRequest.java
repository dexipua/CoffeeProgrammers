package com.school.dto;

import com.school.models.Subject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class SubjectRequest {
    private long id;
    private String name;

    public SubjectRequest(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
    }
}
