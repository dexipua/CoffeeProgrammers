package com.school.service.impl;

import com.school.dto.subject.SubjectRequest;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.models.User;
import com.school.repositories.SubjectRepository;
import com.school.service.StudentService;
import com.school.service.TeacherService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private TeacherService teacherService;
    @Mock
    private StudentService studentService;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Test
    void create() {
        //given
        Subject subject = new Subject();
        subject.setName("Math");
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.setName("Math");
        // when
        subjectService.create(subjectRequest);

        //then
        ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectRepository).save(subjectArgumentCaptor.capture());
        Subject capturedSubject = subjectArgumentCaptor.getValue();

        assertThat(capturedSubject).isEqualTo(subject);
    }

    @Test
    void createWithExistsSubject() {
        //given
        Subject subject = new Subject();
        subject.setName("Math");
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.setName("Math");
        // when
        subjectService.create(subjectRequest);
        when(subjectRepository.findByName("Math")).thenReturn(Optional.of(subject));

        assertThrowsExactly(EntityExistsException.class, () -> subjectService.create(subjectRequest));
    }

    @Test
    void readById() {
//        given
        Subject subject = new Subject();
        subject.setName("Math");
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.setName("Math");
        subjectService.create(subjectRequest);

//         when
        when(subjectRepository.findById(subject.getId())).thenReturn(Optional.of(subject));
        Subject actual = subjectService.findById(subject.getId());

//        then
        assertThat(actual).isEqualTo(subject);
    }

    @Test
    void notReadById() {
        //given
        Subject subject = new Subject();
        subject.setName("Math");
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.setName("Math");
        subjectService.create(subjectRequest);

        //then
        assertThrowsExactly(EntityNotFoundException.class, () -> subjectService.findById(-1));
    }

    @Test
    void update() {
        //given
        Subject subject = new Subject();
        subject.setName("Math");
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.setName("Math");
        //when
        when(subjectRepository.findById(subject.getId())).thenReturn(Optional.of(subject));
        when(subjectRepository.save(subject)).thenReturn(subject);

        Subject updatedSubject = subjectService.update(subject.getId(), subjectRequest);

        //then
        assertEquals(subject, updatedSubject);
        verify(subjectRepository, times(1)).findById(subject.getId());
        verify(subjectRepository, times(1)).save(subject);

    }

    @Test
    void updateWithExistsSubject() {
        //given
        Subject subject = new Subject();
        subject.setName("Math");
        subject.setId(0);
        Subject subject2 = new Subject();
        subject2.setName("Art");
        subject2.setId(1);
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.setName("Math");
        // when
        subjectRepository.save(subject);
        subjectRepository.save(subject2);
        when(subjectRepository.findByName("Math")).thenReturn(Optional.of(subject));
        when(subjectRepository.findById((long)1)).thenReturn(Optional.of(subject2));

        assertThrowsExactly(EntityExistsException.class, () -> subjectService.update(1, subjectRequest));

    }

    @Test
    void setTeacher() {
        // given
        Subject subject = new Subject();
        subject.setName("Math");
        subject.setId(0);
        Teacher teacher = new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        teacher.setId(0);

        // when
        when(subjectRepository.findById((long)0)).thenReturn(Optional.of(subject));
        when(teacherService.findById(0)).thenReturn(teacher);
        // then
        subjectService.setTeacher(subject.getId(), teacher.getId());
        verify(subjectRepository, times(1)).save(subject);
        verify(teacherService, times(1)).findById(0);
        verify(subjectRepository, times(1)).findById((long)0);
    }

    @Test
    void deleteTeacher() {
        // given
        Subject subject = new Subject();
        subject.setName("Math");
        subject.setId(0);
        subject.setTeacher(new Teacher(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123")));

        // when
        when(subjectRepository.findById((long)0)).thenReturn(Optional.of(subject));
        // then
        subjectService.deleteTeacher(subject.getId());
        verify(subjectRepository, times(1)).save(subject);
        verify(subjectRepository, times(1)).findById((long)0);
    }

    @Test
    void addStudent() {
        // given
        Subject subject = new Subject();
        subject.setName("Math");
        subject.setId(0);
        subject.setStudents(new ArrayList<>());
        subject.setTeacher(new Teacher(new User("Vladobrod2", "Vlad", "Bulakovskyi", "Vlad123")));
        Student student = new Student(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        student.setId(0);
        // when
        when(subjectRepository.findById((long)0)).thenReturn(Optional.of(subject));
        when(studentService.findById(0)).thenReturn(student);
        // then
        subjectService.addStudent(subject.getId(), student.getId());
        verify(subjectRepository, times(1)).save(subject);
        verify(subjectRepository, times(1)).findById((long)0);
        verify(studentService, times(1)).findById(0);
    }

    @Test
    void addStudentAlreadyHaveStudent() {
        // given
        Subject subject = new Subject();
        subject.setName("Math");
        subject.setId(0);
        Student student = new Student(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        student.setId(0);
        subject.setStudents(new ArrayList<>(Arrays.asList(student)));
        // when
        when(subjectRepository.findById((long)0)).thenReturn(Optional.of(subject));
        when(studentService.findById(0)).thenReturn(student);
        // then
        assertThrowsExactly(UnsupportedOperationException.class, () -> subjectService.addStudent(subject.getId(), student.getId()));
        verify(subjectRepository, never()).save(subject);
        verify(subjectRepository, times(1)).findById((long)0);
        verify(studentService, times(1)).findById(0);
    }

    @Test
    void deleteStudent() {
        // given
        Subject subject = new Subject();
        subject.setName("Math");
        subject.setId(0);
        Student student = new Student(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        student.setId(0);
        subject.setStudents(new ArrayList<>(Arrays.asList(student)));
        // when
        when(subjectRepository.findById((long)0)).thenReturn(Optional.of(subject));
        when(studentService.findById(0)).thenReturn(student);
        // then
        subjectService.deleteStudent(subject.getId(), student.getId());
        verify(subjectRepository, times(1)).save(subject);
        verify(subjectRepository, times(1)).findById((long)0);
        verify(studentService, times(1)).findById(0);
    }

    @Test
    void deleteStudentHaveNotStudent() {
        // given
        Subject subject = new Subject();
        subject.setName("Math");
        subject.setId(0);
        Student student = new Student(new User("Vladobrod", "Vlad", "Bulakovskyi", "Vlad123"));
        student.setId(0);
        subject.setStudents(new ArrayList<>());
        // when
        when(subjectRepository.findById((long)0)).thenReturn(Optional.of(subject));
        when(studentService.findById(0)).thenReturn(student);
        // then
        assertThrowsExactly(UnsupportedOperationException.class, () -> subjectService.deleteStudent(subject.getId(), student.getId()));
        verify(subjectRepository, never()).save(subject);
        verify(subjectRepository, times(1)).findById((long)0);
        verify(studentService, times(1)).findById(0);
    }

    @Test
    void delete() {
        //given
        Subject subject = new Subject();
        subject.setName("Math");
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.setName("Math");
        subjectService.create(subjectRequest);

        //when
        when(subjectRepository.findById(subject.getId())).thenReturn(Optional.of(subject));
        subjectService.delete(subject.getId());

        //then
        verify(subjectRepository, times(1)).findById(subject.getId());
        verify(subjectRepository, times(1)).delete(subject);
    }

    @Test
    void getAllByOrderByName() {
        //given
        SubjectRequest subjectRequest1 = new SubjectRequest();
        subjectRequest1.setName("Math");
        SubjectRequest subjectRequest2 = new SubjectRequest();
        subjectRequest2.setName("Art");

        Subject subject1 = new Subject();
        subject1.setName("Math");
        Subject subject2 = new Subject();
        subject2.setName("Art");
        List<SubjectRequest> subjectRequests = List.of(
                subjectRequest1,
                subjectRequest2
        );

        List<Subject> subjects = List.of(
                subject1,
                subject2
        );

        subjectService.create(subjectRequests.get(0));
        subjectService.create(subjectRequests.get(1));

        //when
        when(subjectRepository.findAllByOrderByName()).thenReturn(subjects);
        List<Subject> actual = subjectService.getAllByOrderByName();

        //then
        assertThat(actual).isEqualTo(subjects);
    }

    @Test
    void GetNoneByOrderByName() {
        //given
        List<Subject> empty = new ArrayList<>();

        //when
        List<Subject> actual = subjectService.getAllByOrderByName();

        //then
        assertThat(actual).isEqualTo(empty);
    }


    @Test
    void findByName() {
        //given
        Subject subject1 = new Subject("Math");
        SubjectRequest subjectRequest1 = new SubjectRequest();
        subjectRequest1.setName("Math");
        SubjectRequest subjectRequest2 = new SubjectRequest();
        subjectRequest2.setName("Philosophy");

        subjectService.create(subjectRequest1);
        subjectService.create(subjectRequest2);

        //when
        when(subjectRepository.findByNameContainingIgnoreCase("Math")).thenReturn(List.of(subject1));
        List<Subject> actual = subjectService.findByNameContaining("Math");

        //then
        assertThat(actual).isEqualTo(List.of(subject1));
    }

    @Test
    void notFindByName() {
        //given
        SubjectRequest subjectRequest1 = new SubjectRequest();
        subjectRequest1.setName("Math");
        SubjectRequest subjectRequest2 = new SubjectRequest();
        subjectRequest2.setName("Philosophy");

        subjectService.create(subjectRequest1);
        subjectService.create(subjectRequest2);

        //then
        assertEquals(new ArrayList<>(), subjectService.findByNameContaining("---"));
    }

    @Test
    void findByTeacher_Id() {
        //given
        Teacher teacher = new Teacher();

        List<Subject> teacherSubjects = List.of(
                new Subject("Algebra", teacher),
                new Subject("Geometry", teacher)
        );

        SubjectRequest subjectRequest1 = new SubjectRequest();
        subjectRequest1.setName("Math");
        SubjectRequest subjectRequest2 = new SubjectRequest();
        subjectRequest2.setName("Philosophy");

        List<SubjectRequest> teacherSubjectRequests = List.of(
                subjectRequest1,
                subjectRequest2
        );

        SubjectRequest subjectRequest3 = new SubjectRequest();
        subjectRequest3.setName("Philosophy");

        subjectService.create(teacherSubjectRequests.get(0));
        subjectService.create(teacherSubjectRequests.get(1));
        subjectService.create(subjectRequest3);

        //when
        when(subjectRepository.findByTeacher_Id(teacher.getId())).thenReturn(teacherSubjects);
        List<Subject> actual = subjectService.findByTeacher_Id(teacher.getId());

        //then
        assertThat(actual).isEqualTo(teacherSubjects);
    }

    @Test
    void notFindByTeacher_Id() {
        //given
        SubjectRequest subjectRequest1 = new SubjectRequest();
        subjectRequest1.setName("Math");
        SubjectRequest subjectRequest2 = new SubjectRequest();
        subjectRequest2.setName("Philosophy");

        subjectService.create(subjectRequest1);
        subjectService.create(subjectRequest2);

        //then
        assertEquals(List.of(), subjectService.findByTeacher_Id(-1));
    }

    @Test
    void findByStudent_Id() {
        //given
        Student student = new Student();
        Student anotherStudent = new Student();

        List<Subject> studentSubjects = List.of(
                new Subject("Algebra"),
                new Subject("Geometry")
        );
        SubjectRequest subjectRequest1 = new SubjectRequest();
        subjectRequest1.setName("Algebra");
        SubjectRequest subjectRequest2 = new SubjectRequest();
        subjectRequest2.setName("Geometry");
        studentSubjects.get(0).getStudents().add(student);
        studentSubjects.get(1).getStudents().add(student);

        Subject anotherStudentSubject = new Subject("Philosophy");
        SubjectRequest subjectRequest3 = new SubjectRequest();
        subjectRequest3.setName("Philosophy");
        anotherStudentSubject.getStudents().add(anotherStudent);

        subjectService.create(subjectRequest1);
        subjectService.create(subjectRequest2);
        subjectService.create(subjectRequest3);

        //when
        when(subjectRepository.findByStudent_Id(student.getId())).thenReturn(studentSubjects);
        List<Subject> actual = subjectService.findByStudent_Id(student.getId());

        //then
        assertThat(actual).isEqualTo(studentSubjects);
    }

    @Test
    void notFindByStudent_Id() {
        //given
        SubjectRequest subjectRequest1 = new SubjectRequest();
        subjectRequest1.setName("Math");
        SubjectRequest subjectRequest2 = new SubjectRequest();
        subjectRequest2.setName("Philosophy");

        subjectService.create(subjectRequest1);
        subjectService.create(subjectRequest2);

        //then
        assertEquals(List.of(), subjectService.findByStudent_Id(-1));
    }
}