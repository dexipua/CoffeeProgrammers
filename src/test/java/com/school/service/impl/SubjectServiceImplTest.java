package com.school.service.impl;

import com.school.dto.subject.SubjectRequest;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.models.User;
import com.school.repositories.SubjectRepository;
import com.school.service.StudentService;
import com.school.service.TeacherService;
import com.school.service.UserNewsService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
    @Mock
    private UserNewsService userNewsService;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    private Subject subject1;
    private Subject subject2;

    private SubjectRequest subjectRequest1;
    private SubjectRequest subjectRequest2;

    private Teacher teacher;
    private Student student;

    @BeforeEach
    void setUp() {
        subject1 = new Subject();
        subject1.setName("Math");
        subject1.setId(1);

        subject2 = new Subject();
        subject2.setName("Philosophy");
        subject2.setId(2);

        subjectRequest1 = new SubjectRequest();
        subjectRequest1.setName("Math");

        subjectRequest2 = new SubjectRequest();
        subjectRequest2.setName("Philosophy");

        teacher = new Teacher(new User("Vlad", "Bulakovskyi", "vlad1@gmail.com", "Vlad123"));
        teacher.setId(1);

        student = new Student(new User("Vlad", "Bulakovskyi", "vlad2@gmail.com", "Vlad123"));
        student.setId(1);
    }

    @Test
    void create() {
        // when
        subjectService.create(subjectRequest1);

        //then
        ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectRepository).save(subjectArgumentCaptor.capture());
        Subject capturedSubject = subjectArgumentCaptor.getValue();

        assertThat(capturedSubject.getName()).isEqualTo(subject1.getName());
    }

    @Test
    void createWithExistsSubject() {
        // when
        subjectService.create(subjectRequest1);
        when(subjectRepository.findByName(subject1.getName())).thenReturn(Optional.of(subject1));

        assertThrowsExactly(EntityExistsException.class, () -> subjectService.create(subjectRequest1));
    }

    @Test
    void readById() {
        // when
        when(subjectRepository.findById(subject1.getId())).thenReturn(Optional.of(subject1));
        Subject actual = subjectService.findById(subject1.getId());

        // then
        assertThat(actual).isEqualTo(subject1);
    }

    @Test
    void notReadById() {
        //given
        subjectService.create(subjectRequest1);

        //then
        assertThrowsExactly(EntityNotFoundException.class, () -> subjectService.findById(-1));
    }

    @Test
    void update() {
        //when
        when(subjectRepository.findById(subject1.getId())).thenReturn(Optional.of(subject1));
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject2);

        Subject updatedSubject = subjectService.update(subject1.getId(), subjectRequest2);

        //then
        assertEquals(subject2, updatedSubject);
        verify(subjectRepository, times(1)).findById(subject1.getId());
        verify(subjectRepository, times(1)).save(subject1);

    }

    @Test
    void updateEqualsName() {
        //when
        when(subjectRepository.findById(subject1.getId())).thenReturn(Optional.of(subject1));
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject2);

        Subject updatedSubject = subjectService.update(subject1.getId(), subjectRequest1);

        //then
        assertEquals(subject2, updatedSubject);
        verify(subjectRepository, times(1)).findById(subject1.getId());
        verify(subjectRepository, times(1)).save(subject1);

    }

    @Test
    void updateNotPresent() {
        // when
        when(subjectRepository.findByName(subject1.getName())).thenReturn(Optional.empty());
        when(subjectRepository.findById(subject2.getId())).thenReturn(Optional.of(subject2));
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject1);

        assertEquals(subject1, subjectService.update(subject2.getId(), subjectRequest1));

    }

    @Test
    void updateWithExistsSubject() {
        // when
        subjectRepository.save(subject1);
        subjectRepository.save(subject2);
        when(subjectRepository.findByName(subject1.getName())).thenReturn(Optional.of(subject1));
        when(subjectRepository.findById(subject2.getId())).thenReturn(Optional.of(subject2));

        assertThrowsExactly(EntityExistsException.class, () -> subjectService.update(subject2.getId(), subjectRequest1));

    }

    @Test
    void setTeacher() {
        // when
        when(subjectRepository.findById(subject1.getId())).thenReturn(Optional.of(subject1));
        when(teacherService.findById(teacher.getId())).thenReturn(teacher);
        // then
        subjectService.setTeacher(subject1.getId(), teacher.getId());
        verify(subjectRepository, times(1)).save(subject1);
        verify(teacherService, times(1)).findById(teacher.getId());
        verify(subjectRepository, times(1)).findById(subject1.getId());
    }

    @Test
    void deleteTeacher() {
        // given
        subject1.setTeacher(teacher);
        // when
        when(subjectRepository.findById(subject1.getId())).thenReturn(Optional.of(subject1));
        // then
        subjectService.deleteTeacher(subject1.getId());
        verify(subjectRepository, times(1)).save(subject1);
        verify(subjectRepository, times(1)).findById(subject1.getId());
    }

    @Test
    void addStudent() {
        // given
        subject1.setStudents(new ArrayList<>());
        subject1.setTeacher(teacher);
        // when
        when(subjectRepository.findById(subject1.getId())).thenReturn(Optional.of(subject1));
        when(studentService.findById(student.getId())).thenReturn(student);
        // then
        subjectService.addStudent(subject1.getId(), student.getId());
        verify(subjectRepository, times(1)).save(subject1);
        verify(subjectRepository, times(1)).findById(subject1.getId());
        verify(studentService, times(1)).findById(student.getId());
    }

    @Test
    void addStudentAlreadyHaveStudent() {
        // given
        subject1.setStudents(new ArrayList<>(List.of(student)));
        // when
        when(subjectRepository.findById(subject1.getId())).thenReturn(Optional.of(subject1));
        when(studentService.findById(student.getId())).thenReturn(student);
        // then
        assertThrowsExactly(UnsupportedOperationException.class, () -> subjectService.addStudent(subject1.getId(), student.getId()));
        verify(subjectRepository, never()).save(subject1);
        verify(subjectRepository, times(1)).findById(subject1.getId());
        verify(studentService, times(1)).findById(student.getId());
    }

    @Test
    void deleteStudent() {
        // given
        subject1.setStudents(new ArrayList<>(List.of(student)));
        // when
        when(subjectRepository.findById(subject1.getId())).thenReturn(Optional.of(subject1));
        when(studentService.findById(student.getId())).thenReturn(student);
        // then
        subjectService.deleteStudent(subject1.getId(), student.getId());
        verify(subjectRepository, times(1)).save(subject1);
        verify(subjectRepository, times(1)).findById(subject1.getId());
        verify(studentService, times(1)).findById(student.getId());
    }

    @Test
    void deleteStudentHaveNotStudent() {
        // given
        subject1.setStudents(new ArrayList<>());
        // when
        when(subjectRepository.findById(subject1.getId())).thenReturn(Optional.of(subject1));
        when(studentService.findById(student.getId())).thenReturn(student);
        // then
        assertThrowsExactly(UnsupportedOperationException.class, () -> subjectService.deleteStudent(subject1.getId(), student.getId()));
        verify(subjectRepository, never()).save(subject1);
        verify(subjectRepository, times(1)).findById(subject1.getId());
        verify(studentService, times(1)).findById(student.getId());
    }

    @Test
    void delete() {
        //when
        when(subjectRepository.findById(subject1.getId())).thenReturn(Optional.of(subject1));
        subjectService.delete(subject1.getId());

        //then
        verify(subjectRepository, times(1)).findById(subject1.getId());
        verify(subjectRepository, times(1)).delete(subject1);
    }

    @Test
    void getAllByOrderByName() {
        //given
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
    void getNoneByOrderByName() {
        //given
        List<Subject> empty = new ArrayList<>();

        //when
        List<Subject> actual = subjectService.getAllByOrderByName();

        //then
        assertThat(actual).isEqualTo(empty);
    }


    @Test
    void findByName() {
        //when
        when(subjectRepository.findByNameContainingIgnoreCase(subject1.getName())).thenReturn(List.of(subject1));
        List<Subject> actual = subjectService.findByNameContaining(subject1.getName());

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
        // given

        List<Subject> teacherSubjects = List.of(
                subject1,
                subject2
        );

        List<SubjectRequest> teacherSubjectRequests = List.of(
                subjectRequest1,
                subjectRequest2
        );

        SubjectRequest subjectRequest3 = new SubjectRequest();
        subjectRequest3.setName("Art");

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
        subject1.setStudents(new ArrayList<>());
        subject2.setStudents(new ArrayList<>());
        List<Subject> studentSubjects = List.of(
                subject1,
                subject2
        );
        studentSubjects.get(0).getStudents().add(student);
        studentSubjects.get(1).getStudents().add(student);

        Subject anotherStudentSubject = new Subject("Art");
        SubjectRequest subjectRequest3 = new SubjectRequest();
        subjectRequest3.setName("Art");
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
        // given
        subjectService.create(subjectRequest1);
        subjectService.create(subjectRequest2);

        //then
        assertEquals(List.of(), subjectService.findByStudent_Id(-1));
    }
}