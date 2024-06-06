package com.school.service.impl;


import com.school.dto.user.UserRequestCreate;
import com.school.dto.user.UserRequestUpdate;
import com.school.models.Student;
import com.school.models.User;
import com.school.repositories.StudentRepository;
import com.school.service.RoleService;
import com.school.service.StudentService;
import com.school.service.TeacherService;
import com.school.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UserService userService;
    private final StudentRepository studentRepository;
    private final RoleService roleService;
    private final TeacherService teacherService;

    @Override
    public Student create(UserRequestCreate userRequest) {
        User userToCreate = UserRequestCreate.toUser(userRequest);
        userToCreate.setRole(roleService.findByName("STUDENT"));
        userService.create(userToCreate);
        return studentRepository.save(new Student(userToCreate));
    }

    @Override
    public Student findById(long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Student with id " + id + " not found"));
    }

    @Override
    public Student update(long studentToUpdateId, UserRequestUpdate userRequest) {
        Student studentToUpdate = findById(studentToUpdateId);
        userService.update(studentToUpdate.getUser().getId(), userRequest);
        return studentToUpdate;
    }

    @Override
    public void deleteById(long id) {
        Student student = findById(id);
        studentRepository.delete(student);
    }

    @Override
    public List<Student> findAllOrderedByName() {
        return studentRepository.findAllByOrderByUser_LastNameAsc();
    }


    @Override
    public List<Student> findBySubjectName(String subjectName) {
        return studentRepository.findStudentBySubjectNameContainingIgnoreCase(subjectName);
    }

    @Override
    public List<Student> findStudentsByTeacherId(long teacherId) {
        teacherService.findById(teacherId);
        return studentRepository.findAllByTeacherId(teacherId);

    }

    @Override
    public List<Student> findAllByUser_FirstNameAndAndUser_LastName(
            String firstName,
            String lastName) {
        return studentRepository.findAllByUser_FirstNameContainingIgnoreCaseAndUser_LastNameContainingIgnoreCase(firstName, lastName);
    }
}
