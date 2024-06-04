package com.school.dto.subject;

import com.school.models.Subject;
import lombok.Data;

@Data
public class SubjectResponseSimple {
    long id;
    String name;

    public SubjectResponseSimple(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
    }
}
