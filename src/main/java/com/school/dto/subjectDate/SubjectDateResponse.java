package com.school.dto.subjectDate;

import com.school.dto.subject.SubjectResponseToGet;
import com.school.models.SubjectDate;
import lombok.Data;

@Data
public class SubjectDateResponse {
    private long id;
    private String dayOfWeek;
    private String numOfLesson;
    private SubjectResponseToGet subjectResponseToGet;
    public SubjectDateResponse(SubjectDate s) {
        this.id = s.getId();
        this.dayOfWeek = s.getDayOfWeek().toString();
        this.numOfLesson = s.getNumOfLesson().toString();
        this.subjectResponseToGet = new SubjectResponseToGet(s.getSubject());
    }
}
