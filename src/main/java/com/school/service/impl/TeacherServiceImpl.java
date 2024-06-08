package com.school.service.impl;

import com.school.dto.user.UserRequestCreate;
import com.school.dto.user.UserRequestUpdate;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.models.User;
import com.school.repositories.TeacherRepository;
import com.school.service.RoleService;
import com.school.service.TeacherService;
import com.school.service.UserService;
import jakarta.persistence.EntityNotFoundException;
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
    public Teacher create(UserRequestCreate userRequest) {
        User userToCreate = UserRequestCreate.toUser(userRequest);
        userToCreate.setRole(roleService.findByName("STUDENT"));
        userService.create(userToCreate);
        return teacherRepository.save(new Teacher(userToCreate));
    }

    @Override
    public Teacher findById(long id) {
        return teacherRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Teacher with id " + id + " not found"));
    }

    @Override
    public Teacher update(long teacherToUpdateId, UserRequestUpdate userRequest) {
        Teacher teacherToUpdate = findById(teacherToUpdateId);
        userService.update(teacherToUpdate.getUser().getId(), userRequest);
        return teacherToUpdate;
    }

    @Override
    public void delete(long id) {
        Teacher teacher = findById(id);
        if (teacher.getUser().getRole().getName().equals("CHIEF_TEACHER")) {
            throw new UnsupportedOperationException(
                    "Cannot delete teacher with role CHIEF_TEACHER");
        } else {
            List<Subject> subjects = teacher.getSubjects();
            subjects.forEach(subject -> subject.setTeacher(null));
        }
        teacher.setSubjects(new ArrayList<>());
        teacherRepository.delete(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAllByOrderByUser_LastNameAsc();
    }

    @Override
    public List<Teacher> findBySubjectName(String subjectName) {
        return teacherRepository.findBySubjectNameIgnoreCase(subjectName);
    }

    @Override
    public List<Teacher> findAllByUser_FirstNameAndAndUser_LastName(String firstName, String lastName) {
        return teacherRepository.findAllByUser_FirstNameContainingIgnoreCaseAndUser_LastNameContainingIgnoreCase(firstName, lastName);
    }

    @Override
    public Teacher findByUserId(long userId) {
        return teacherRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Teacher with id " + userId + " not found"));
    }
}
