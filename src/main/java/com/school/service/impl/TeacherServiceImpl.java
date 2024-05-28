package com.school.service.impl;

import com.school.exception.TeacherExistException;
import com.school.exception.TeacherNotFoundException;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.repositories.RoleRepository;
import com.school.repositories.StudentRepository;
import com.school.repositories.SubjectRepository;
import com.school.repositories.TeacherRepository;
import com.school.service.TeacherService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public Teacher create(@NotNull Teacher teacher) {
        if(teacherRepository.findByUserEmail(teacher.getUser().getEmail()).isPresent()){
            throw new TeacherExistException("Teacher already exists");
        }
        teacher.getUser().setRole(roleRepository.findByName("TEACHER").get());
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher findById(long id) {
        return teacherRepository.findById(id).orElseThrow(
                () -> new TeacherNotFoundException("Teacher with id " + id + " not found"));
    }

    @Override
    public Teacher update(@NotNull Teacher teacher) {
        if(teacherRepository.findByUserEmail(teacher.getUser().getEmail()).isPresent()){
            if(!(teacher.getUser().getEmail().equals(teacherRepository.findById(teacher.getId()).get().getUser().getEmail()))){
                throw new TeacherExistException("Teacher already exists");
            }
        }
        findById(teacher.getId());
        return teacherRepository.save(teacher);
    }

    @Override
    public void delete(long id) {
        Teacher teacher = findById(id);
        if(teacher.getUser().getRole().getName().equals("CHIEF_TEACHER")){
            throw new TeacherExistException("Cannot delete teacher with role CHIEF_TEACHER");
        }
        if(subjectRepository.findByTeacher_Id(id).isPresent()) {
            List<Subject> subjects = subjectRepository.findByTeacher_Id(id).get();
            subjects.forEach(a -> a.setTeacher(null));
        }
        teacher.setSubjects(null);
        teacherRepository.delete(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        Optional<List<Teacher>> teachers = teacherRepository.findAllByOrderByUser();
        return teachers.orElseGet(ArrayList::new);
    }

    @Override
    public Teacher findBySubjectName(String subjectName){
        Optional<Teacher> teacher = teacherRepository.findBySubjectsContains(subjectName);
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Teacher with subject name " + subjectName + " not found");
        }
        return teacher.get();
    }

    @Override
    public Teacher findByEmail(String email){
        Optional<Teacher> teacher = teacherRepository.findByUserEmail(email);
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Teacher not found with such email" + email);
        }
        return teacher.get();
    }
}
