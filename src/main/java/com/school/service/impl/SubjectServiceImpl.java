package com.school.service.impl;

import com.school.dto.subject.SubjectRequest;
import com.school.models.*;
import com.school.repositories.StudentRepository;
import com.school.repositories.SubjectDateRepository;
import com.school.repositories.SubjectRepository;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import com.school.service.TeacherService;
import com.school.service.UserNewsService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectDateRepository subjectDateRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final UserNewsService userNewsService;

    @Override
    public Subject create(SubjectRequest subjectRequest) {
        if (subjectRepository.findByName(subjectRequest.getName()).isPresent()) {
            throw new EntityExistsException(
                    "Subject with name " + subjectRequest.getName() + " already exists"
            );
        }
        return subjectRepository.save(SubjectRequest.toSubject(subjectRequest));
    }

    @Override
    public Subject findById(long id) {
        return subjectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Subject with id " + id + " not found"));
    }

    @Override
    public Subject update(long subjectToUpdateId, SubjectRequest subjectRequest) {
        Subject subjectToUpdate = findById(subjectToUpdateId);

        String updatedName = subjectRequest.getName();
        String actualName = subjectToUpdate.getName();

        if (!updatedName.equals(actualName) &&
                subjectRepository.findByName(updatedName).isPresent()) {
            throw new EntityExistsException(
                    "Subject with name " + updatedName + " already exists"
            );
        }
        subjectToUpdate.setName(subjectRequest.getName());

        return subjectRepository.save(subjectToUpdate);
    }

    @Override
    public void delete(long id) {
        Subject subject = findById(id);
        subject.setStudents(null);
        subjectRepository.delete(subject);
    }

    @Override
    public List<Subject> getAllByOrderByName() {
        return subjectRepository.findAllByOrderByName();
    }

    @Override
    public List<Subject> findByNameContaining(String name) {
        return subjectRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Subject> findByTeacher_Id(long teacherId) {
        teacherService.findById(teacherId);
        return subjectRepository.findByTeacher_Id(teacherId);
    }

    @Override
    public void deleteTeacher(long subjectId) {

        Subject subject = findById(subjectId);

        userNewsService.create(
                new UserNews("You have been removed from a subject: " + subject.getName(),
                        subject.getTeacher().getUser()
                )
        );

        subject.setTeacher(null);

        subjectRepository.save(subject);
    }

    @Override
    public void setTeacher(long subjectId, long teacherId) {
        Subject subject = findById(subjectId);
        Teacher teacher = teacherService.findById(teacherId);
        List<SubjectDate> subjectDatesSubject = subjectDateRepository.findAllBySubject_Id(subjectId);

        for(SubjectDate subjectDate : subjectDatesSubject){
            if(!subjectDateRepository.findAllByDayOfWeekAndNumOfLessonAndSubject_IdIsNotAndSubject_Teacher(
                    subjectDate.getDayOfWeek(),
                    subjectDate.getNumOfLesson(),
                    subjectId,
                    teacher
            ).isEmpty()){
                throw new UnsupportedOperationException("This teacher already have time at time which have this subject");
            }
        }

        userNewsService.create(
                new UserNews("You have become a teacher of the subject: " + subject.getName(),
                        teacher.getUser()
                )
        );

        subject.setTeacher(teacher);

        subjectRepository.save(subject);
    }

    @Override
    public List<Subject> findByStudent_Id(long studentId) {
        studentService.findById(studentId);
        return subjectRepository.findByStudent_Id(studentId);
    }

    @Override
    public void addStudent(long subjectId, long studentId) {
        Subject subject = findById(subjectId);
        Student student = studentService.findById(studentId);

        student.getSubjects().add(subject);

        userNewsService.create(new UserNews("You have been added to subject: " + subject.getName(), student.getUser()));
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long subjectId, long studentId) {
        Subject subject = findById(subjectId);
        Student student = studentService.findById(studentId);

        if (!subject.getStudents().contains(student)) {
            throw new UnsupportedOperationException(
                    "Subject doesn't have this student"
            );
        }

        student.getSubjects().remove(subject);

        userNewsService.create(new UserNews("You have been deleted from subject " + subject.getName(), student.getUser()));
        subjectRepository.save(subject);
    }
}
