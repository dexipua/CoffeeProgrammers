package com.school.service.impl;

import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.repositories.TeacherRepository;
import com.school.service.RoleService;
import com.school.service.TeacherService;
import com.school.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final UserService userService;
    private final TeacherRepository teacherRepository;
    private final RoleService roleService;

    @Override
    public Teacher create(@NotNull Teacher teacher) {
        teacher.getUser().setRole(roleService.findByName("TEACHER"));
        userService.create(teacher.getUser());
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher findById(long id) {
        return teacherRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Teacher with id " + id + " not found"));
    }

    @Override
    public Teacher update(@NotNull Teacher teacher) {
        Teacher oldTeacher = findById(teacher.getId());
        teacher.getUser().setEmail(oldTeacher.getUser().getEmail());
        teacher.getUser().setId(oldTeacher.getUser().getId());
        teacher.getUser().setRole(oldTeacher.getUser().getRole());
        teacher.setSubjects(oldTeacher.getSubjects());
        userService.update(teacher.getUser());
        return teacher;
    }

    @Override
    public void delete(long id) {
        Teacher teacher = findById(id);
        if (teacher.getUser().getRole().getName().equals("CHIEF_TEACHER")) {
            throw new EntityExistsException("Cannot delete teacher with role CHIEF_TEACHER");
        } else {
            List<Subject> subjects = teacher.getSubjects();
            subjects.forEach(subject -> subject.setTeacher(null));
        }
        teacher.setSubjects(new ArrayList<>());
        teacherRepository.delete(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAllByOrderByUser();
    }

    @Override
    public Teacher findBySubjectName(String subjectName) {
        return teacherRepository.findBySubjectName(subjectName).orElseThrow(
                () -> new EntityNotFoundException("Teacher with subject name " + subjectName + " not found")
        );
    }

    @Override
    public Teacher findByEmail(String email) {
        return teacherRepository.findByUserEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Teacher not found with such email" + email)
        );
    }

    @Override
    public List<Teacher> findAllByUser_FirstNameAndAndUser_LastName(String firstName, String lastName) {
        return teacherRepository.findAllByUser_FirstNameAndAndUser_LastName(firstName, lastName);
    }
}
