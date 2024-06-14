package com.school.dto.home;

import lombok.Data;

@Data
public class HomeResponse {
//    private List<TeacherResponseSimple> teacherResponseSimpleList;
//    private List<SubjectResponseSimple> subjectResponseAllList;
    private long amountOfStudents;

    public HomeResponse(long amountOfStudents) {
//        this.teacherResponseSimpleList = teacherResponseSimpleList;
//        this.subjectResponseAllList = subjectResponseAllList;
        this.amountOfStudents = amountOfStudents;
    }
}
