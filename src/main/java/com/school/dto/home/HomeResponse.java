package com.school.dto.home;

import com.school.dto.subject.SubjectResponseSimple;
import com.school.dto.teacher.TeacherResponseSimple;
import lombok.Data;

import java.util.List;

@Data
public class HomeResponse {
    private List<TeacherResponseSimple> teacherResponseSimpleList;
    private List<SubjectResponseSimple> subjectResponseAllList;
    private long amountOfStudents;

    public HomeResponse(List<TeacherResponseSimple> teacherResponseSimpleList, List<SubjectResponseSimple> subjectResponseAllList, long amountOfStudents) {
        this.teacherResponseSimpleList = teacherResponseSimpleList;
        this.subjectResponseAllList = subjectResponseAllList;
        this.amountOfStudents = amountOfStudents;
    }
}
