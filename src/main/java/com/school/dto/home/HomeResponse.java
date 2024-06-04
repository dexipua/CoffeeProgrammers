package com.school.dto.home;

import com.school.dto.subject.SubjectResponseToGet;
import com.school.dto.teacher.TeacherResponseToGet;
import lombok.Data;

import java.util.List;

@Data
public class HomeResponse {
    private List<TeacherResponseToGet> teacherResponseToGetList;
    private List<SubjectResponseToGet> subjectResponseAllList;
    private long amountOfStudents;

    public HomeResponse(List<TeacherResponseToGet> teacherResponseToGetList, List<SubjectResponseToGet> subjectResponseAllList, long amountOfStudents) {
        this.teacherResponseToGetList = teacherResponseToGetList;
        this.subjectResponseAllList = subjectResponseAllList;
        this.amountOfStudents = amountOfStudents;
    }
}
