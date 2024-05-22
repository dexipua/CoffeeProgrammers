package com.school.service.impl;

import com.school.models.Role;
import com.school.models.Teacher;
import com.school.repositories.RoleRepository;
import com.school.repositories.TeacherRepository;
import com.school.repositories.UserRepository;
import com.school.service.RoleService;
import com.school.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;

    @Override
    public Teacher create(Teacher teacher) {
        Optional<Role> role = roleRepository.findByName("TEACHER");
        if(!role.isPresent()) {
            throw new EntityNotFoundException("Role not found");
        }
        teacher.getUser().setRole(role.get());
        if (teacher != null) {
            return teacherRepository.save(teacher);
        }
        throw new EntityNotFoundException("Teacher not found");
    }

    @Override
    public Teacher findById(long id) {
        return teacherRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Teacher with id " + id + " not found"));
    }

    @Override
    public Teacher update(Teacher teacher) {
        if (teacher != null) {
            findById(teacher.getId());
            return teacherRepository.save(teacher);
        }
        throw new EntityNotFoundException("Teacher not found");
    }

    @Override
    public void delete(long id) {
        Teacher teacher = findById(id);
        teacherRepository.delete(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findBySubjectName(String subjectName){
        Optional<Teacher> teacher = teacherRepository.findBySubjectName(subjectName);
        if (!teacher.isPresent()) {
            throw new EntityNotFoundException("Teacher not found");
        }
        return teacher.get();
    }

    @Override
    public Teacher findByUsername(String username){
        Optional<Teacher> teacher = teacherRepository.findByUsername(username);
        if (!teacher.isPresent()) {
            throw new EntityNotFoundException("Teacher not found");
        }
        return teacher.get();
    }

    @Override
    public Teacher findByEmail(String email){
        Optional<Teacher> teacher = teacherRepository.findByEmail(email);
        if (!teacher.isPresent()) {
            throw new EntityNotFoundException("Teacher not found");
        }
        return teacher.get();
    }
}
