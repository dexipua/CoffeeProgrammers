package com.school.controller;

import com.school.dto.subjectDate.SubjectDateRequest;
import com.school.dto.subjectDate.SubjectDateResponse;
import com.school.dto.subjectDate.SubjectDateResponseByStudentId;
import com.school.dto.subjectDate.SubjectDateResponseBySubjectId;
import com.school.models.Subject;
import com.school.models.SubjectDate;
import com.school.service.SubjectDateService;
import com.school.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/timeTable")
@RequiredArgsConstructor
public class SubjectDateController {

    private final SubjectDateService subjectDateService;
    private final SubjectService subjectService;

    @PostMapping("/create/subject/{subject_id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    public SubjectDateResponse createSubjectDate(
            @Valid @RequestBody SubjectDateRequest subjectDateRequest,
            @PathVariable("subject_id") long subjectId) {

        return new SubjectDateResponse(subjectDateService.create(
                SubjectDateRequest.toSubject(subjectDateRequest, subjectService.findById(subjectId))));
    }

    @PutMapping("/update/{subject_date_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
    public SubjectDateResponse updateSubjectDate(@Valid @RequestBody SubjectDateRequest subjectDateRequest,
                                                 @PathVariable("subject_date_id") long subjectDateId) {
        long subjectId = subjectDateService.findById(subjectDateId).getId();
        SubjectDate subjectDate = SubjectDateRequest.toSubject(subjectDateRequest,
                subjectService.findById(subjectId));
        subjectDate.setId(subjectDateId);
        return new SubjectDateResponse(subjectDateService.update(subjectDate));
    }


    @DeleteMapping("/delete/{subject_date_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_CHIEF_TEACHER')")
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
        TreeMap<DayOfWeek, TreeMap<SubjectDate.NumOfLesson, Subject>> treeMap =
                subjectDateService.findAllBySubject_Students_Id(student_id);
        List<SubjectDateResponseByStudentId> subjectDateResponseByStudentIds = new ArrayList<>();
        treeMap.forEach((key, value) -> value.entrySet().stream()
                .map(inEntry ->
                        new SubjectDateResponseByStudentId(
                                key,
                                inEntry.getKey(),
                                inEntry.getValue()
                        )
                )
                .forEach(subjectDateResponseByStudentIds::add));
        return subjectDateResponseByStudentIds;
    }

    @GetMapping("/getAllBySubject/{subject_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectDateResponseBySubjectId> getAllBySubjectId(@PathVariable("subject_id") int subject_id) {
        TreeMap<DayOfWeek, TreeMap<SubjectDate.NumOfLesson, Long>> treeMap =
                subjectDateService.findAllBySubject_Id(subject_id);
        List<SubjectDateResponseBySubjectId> subjectDateResponseBySubjectIds = new ArrayList<>();
        treeMap.forEach((key, value) -> value.entrySet().stream()
                .map(inEntry ->
                        new SubjectDateResponseBySubjectId(
                                key,
                                inEntry.getKey(),
                                inEntry.getValue()
                        )
                )
                .forEach(subjectDateResponseBySubjectIds::add));
        return subjectDateResponseBySubjectIds;
    }
}
