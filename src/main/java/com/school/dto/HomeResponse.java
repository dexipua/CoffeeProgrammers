package com.school.dto;

import com.school.dto.subject.SubjectResponse;
import com.school.dto.teacher.TeacherResponseToGet;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import com.school.service.TeacherService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HomeResponse {
    private List<TeacherResponseToGet> teacherResponseToGetList = new ArrayList<>();
    private List<SubjectResponse> subjectResponseList = new ArrayList<>();
    private long amountOfStudents;

    public HomeResponse(TeacherService teacherService, SubjectService subjectService, StudentService studentService) {
        List<Teacher> teachers = teacherService.findAll();
        for (Teacher teacher : teachers) {
            teacherResponseToGetList.add(new TeacherResponseToGet(teacher));
        }
        List<Subject> subjects = subjectService.getAllByOrderByName();
        for (Subject subject : subjects) {
            subjectResponseList.add(new SubjectResponse(subject));
        }
        this.amountOfStudents = studentService.findAllOrderedByName().size();
    }
}
