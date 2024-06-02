package com.school.controller;

import com.school.dto.subjectDate.SubjectDateResponseByStudentId;
import com.school.models.Subject;
import com.school.models.SubjectDate;
import com.school.service.SubjectDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/timeTable")
@RequiredArgsConstructor
public class SubjectDateController {

    private final SubjectDateService subjectDateService;

    @GetMapping("/getAllByStudent/{student_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectDateResponseByStudentId> getAllByStudentId(@PathVariable("student_id") int student_id) {
        HashMap<DayOfWeek, HashMap<SubjectDate.NumOfLesson, Subject>> hashMap =
                subjectDateService.findAllBySubject_Students_Id(student_id);
        List<SubjectDateResponseByStudentId> subjectDateResponseByStudentIds = new ArrayList<>();
        for (Map.Entry<DayOfWeek , HashMap<SubjectDate.NumOfLesson, Subject>> entry : hashMap.entrySet()) {
            for (Map.Entry<SubjectDate.NumOfLesson, Subject> subjectEntry : entry.getValue().entrySet()) {
                subjectDateResponseByStudentIds.add(new SubjectDateResponseByStudentId(entry.getKey(),
                        subjectEntry.getKey(), subjectEntry.getValue()));
            }
        }
        return subjectDateResponseByStudentIds;
    }
}
