package com.school.service.impl;

import com.school.dto.mark.MarkRequest;
import com.school.models.Mark;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.UserNews;
import com.school.repositories.MarkRepository;
import com.school.service.MarkService;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import com.school.service.UserNewsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;
    private final SubjectService subjectService;
    private final StudentService studentService;
    private final UserNewsService userNewsService;

    @Transactional
    @Override
    public void deleteAllByStudentId(Long id) {
        markRepository.deleteAllByStudentId(id);
    }

    @Transactional
    @Override
    public void deleteAllBySubjectId(Long id) {
        markRepository.deleteAllBySubjectId(id);
    }

    @Override
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
        userNewsService.create(new UserNews("You got a mark " + mark.getValue() + " in the subject " + subject.getName() , student.getUser()));
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
        userNewsService.create(new UserNews("Your mark " + markToUpdate.getValue() + "in the subject " +
                markToUpdate.getSubject().getName() + "have been updated",
                markToUpdate.getStudent().getUser()));
        return markRepository.save(markToUpdate);
    }

    @Override
    public void delete(long id) {
        Mark mark = findById(id);
        markRepository.delete(mark);
    }

    @Override
    public HashMap<Subject, List<Mark>> findAllByStudentId(long studentId) {
        Set<Subject> subjects = studentService.findById(studentId).getSubjects();
        List<Mark> marks = markRepository.findAllByStudent_Id(studentId);

        HashMap<Subject, List<Mark>> map = new HashMap<>();
        List<Mark> temp;
        for (Subject subject : subjects) {
            temp = map.getOrDefault(subject, new ArrayList<>());
            temp.addAll(marks.stream().filter(mark -> mark.getSubject().getId() == subject.getId()).toList());
            map.put(subject, temp);
        }
        return map;
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
