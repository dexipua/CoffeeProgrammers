package com.school.controller;

import com.school.dto.subjectDate.SubjectDateRequest;
import com.school.dto.subjectDate.SubjectDateResponse;
import com.school.dto.subjectDate.SubjectDateResponseByStudentId;
import com.school.models.Subject;
import com.school.models.SubjectDate;
import com.school.service.SubjectDateService;
import com.school.service.SubjectService;
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
    private final SubjectService subjectService;

    @PostMapping("/create/subject/{subject_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDateResponse createSubjectDate(@RequestBody SubjectDateRequest subjectDateRequest,
                                                 @PathVariable("subject_id") long subjectId) {
        return new SubjectDateResponse(subjectDateService.create(
                SubjectDateRequest.toSubject(subjectDateRequest, subjectService, subjectId)));
    }

    @PutMapping("/update/{subject_date_id}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectDateResponse updateSubjectDate(@RequestBody SubjectDateRequest subjectDateRequest,
                                                 @PathVariable("subject_date_id") long subjectDateId) {
        return new SubjectDateResponse(subjectDateService.update(SubjectDateRequest.toSubject(subjectDateRequest,
                subjectService, subjectDateId)));
    }

    @DeleteMapping("/delete/{subject_date_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSubjectDate(@PathVariable("subject_date_id") long subjectDateId) {
        subjectDateService.delete(subjectDateService.findById(subjectDateId));
    }

    @GetMapping("/getById/{subject_date_id}")
    @ResponseStatus(HttpStatus.OK)
    public SubjectDateResponse getById(@PathVariable("subject_date_id") long subjectDateId) {
        return new SubjectDateResponse(subjectDateService.findById(subjectDateId));
    }

    @GetMapping("/getAllByStudent/{student_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectDateResponseByStudentId> getAllByStudentId(@PathVariable("student_id") int student_id) {
        HashMap<DayOfWeek, HashMap<SubjectDate.NumOfLesson, Subject>> hashMap =
                subjectDateService.findAllBySubject_Students_Id(student_id);
        List<SubjectDateResponseByStudentId> subjectDateResponseByStudentIds = new ArrayList<>();
        for (Map.Entry<DayOfWeek, HashMap<SubjectDate.NumOfLesson, Subject>> entry : hashMap.entrySet()) {
            for (Map.Entry<SubjectDate.NumOfLesson, Subject> inEntry : entry.getValue().entrySet()) {
                subjectDateResponseByStudentIds.add(new SubjectDateResponseByStudentId(entry.getKey(), inEntry.getKey(), inEntry.getValue()));
            }
        }
        return subjectDateResponseByStudentIds;
    }
}
