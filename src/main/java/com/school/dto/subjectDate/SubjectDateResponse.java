package com.school.dto.subjectDate;

import com.school.dto.subject.SubjectResponseSimple;
import com.school.models.SubjectDate;
import lombok.Data;

@Data
public class SubjectDateResponse {
    private long id;
    private String dayOfWeek;
    private String numOfLesson;
    private SubjectResponseSimple subjectResponseSimple;
    public SubjectDateResponse(SubjectDate s) {
        this.id = s.getId();
        this.dayOfWeek = s.getDayOfWeek().toString();
        this.numOfLesson = s.getNumOfLesson().toString();
        this.subjectResponseSimple = new SubjectResponseSimple(s.getSubject());
    }
}
