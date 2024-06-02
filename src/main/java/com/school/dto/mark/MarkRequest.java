package com.school.dto.mark;

import com.school.models.Mark;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class MarkRequest {
    private int mark;

    public static Mark toMark(MarkRequest markRequest, long subject_id, long student_id,
                              SubjectService subjectService, StudentService studentService) {
        Mark result = new Mark();
        result.setMark(markRequest.getMark());
        result.setSubject(subjectService.findById(subject_id));
        result.setStudent(studentService.findById(student_id));
        result.setTime(LocalDateTime.now());
        return result;
    }
    public static Mark toMark(MarkRequest markRequest){
        Mark result = new Mark();
        result.setMark(markRequest.getMark());
        return result;
    }
}
