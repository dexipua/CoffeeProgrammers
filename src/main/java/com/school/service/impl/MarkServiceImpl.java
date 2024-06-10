package com.school.service.impl;

import com.school.dto.mark.MarkRequest;
import com.school.models.Mark;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.repositories.MarkRepository;
import com.school.service.MarkService;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;
    private final SubjectService subjectService;
    private final StudentService studentService;

    @Override //TODO
    public Mark create(
            MarkRequest markRequest,
            long subjectId,
            long studentId
    ) {
        Subject subject = subjectService.findById(subjectId);
        Student student = studentService.findById(studentId);
        if(!subject.getStudents().contains(student)){
            throw new UnsupportedOperationException("Student with id " + studentId + " does not have subject with id " + subjectId);
        }
        Mark mark = MarkRequest.toMark(markRequest);
        mark.setSubject(subjectService.findById(subjectId));
        mark.setStudent(studentService.findById(studentId));
        return markRepository.save(mark);
    }

    @Override
    public Mark findById(long id) {
        return markRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Mark with id " + id + " not found"));
    }

    @Override
    public Mark update(long markToUpdateId, MarkRequest markRequest) {
        Mark markToUpdate = findById(markToUpdateId);
        markToUpdate.setValue(markRequest.getValue());
        markToUpdate.setTime(LocalDateTime.now());
        return markRepository.save(markToUpdate);
    }

    @Override
    public void delete(long id) {
        Mark mark = findById(id);
        markRepository.delete(mark);
    }

    @Override
    public HashMap<Subject, List<Mark>> findAllByStudentId(long studentId) {
        studentService.findById(studentId);

        List<Mark> marks = markRepository.findAllByStudent_Id(studentId);
        return marks.stream()
                .collect(Collectors.groupingBy(
                        Mark::getSubject,
                        HashMap::new,
                        Collectors.toList())
                );
    }

    @Override
    public HashMap<Student, List<Mark>> findAllBySubjectId(long subjectId) {
        subjectService.findById(subjectId);

        List<Mark> marks = markRepository.findAllBySubject_Id(subjectId);
        return marks.stream()
                .collect(Collectors.groupingBy(
                        Mark::getStudent,
                        HashMap::new,
                        Collectors.toList())
                );
    }

    @Override
    public double findAverageByStudentId(long studentId) {
        studentService.findById(studentId);

        List<Mark> marks = markRepository.findAllByStudent_Id(studentId);
        return marks.stream()
                .mapToInt(Mark::getValue)
                .average()
                .orElse(0.0);
    }
}
