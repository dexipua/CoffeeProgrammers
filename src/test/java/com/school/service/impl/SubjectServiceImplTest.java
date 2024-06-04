//package com.school.service.impl;
//
//import com.school.models.Student;
//import com.school.models.Subject;
//import com.school.models.Teacher;
//import com.school.repositories.SubjectRepository;
//import com.school.service.StudentService;
//import com.school.service.TeacherService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class SubjectServiceImplTest {
//
//    @Mock
//    private SubjectRepository subjectRepository;
//    @Mock
//    private TeacherService teacherService;
//    @Mock
//    private StudentService studentService;
//    private SubjectServiceImpl subjectService;
//
//    @BeforeEach
//    void setUp() {
//        subjectService = new SubjectServiceImpl(
//                subjectRepository,
//                teacherService,
//                studentService
//        );
//    }
//
//    @Test
//    void create() {
//        //given
//        Subject subject = new Subject();
//
//        // when
//        subjectService.create(subject);
//
//        //then
//        ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
//        verify(subjectRepository).save(subjectArgumentCaptor.capture());
//        Subject capturedSubject = subjectArgumentCaptor.getValue();
//
//        assertThat(capturedSubject).isEqualTo(subject);
//    }
//
//    @Test
//    void readById() {
//        //given
//        Subject subject = new Subject();
//        subjectService.create(subject);
//
//        // when
//        when(subjectRepository.findById(subject.getId())).thenReturn(Optional.of(subject));
//        Subject actual = subjectService.findById(subject.getId());
//
//        //then
//        assertThat(actual).isEqualTo(subject);
//    }
//
//    @Test
//    void notReadById() {
//        //given
//        Subject subject = new Subject();
//        subjectService.create(subject);
//
//        //then
//        assertThrowsExactly(IllegalArgumentException.class, () -> subjectService.findById(-1));
//    }
//
//    @Test
//    void update() {
//        //given
//        Subject subject = new Subject();
//
//        //when
//        when(subjectRepository.findById(subject.getId())).thenReturn(Optional.of(subject));
//        when(subjectRepository.save(subject)).thenReturn(subject);
//
//        Subject updatedSubject = subjectService.update(subject);
//
//        //then
//        assertEquals(subject, updatedSubject);
//        verify(subjectRepository, times(1)).findById(subject.getId());
//        verify(subjectRepository, times(1)).save(subject);
//
//    }
//
//    @Test
//    void delete() {
//        //given
//        Subject subject = new Subject();
//        subjectService.create(subject);
//
//        //when
//        when(subjectRepository.findById(subject.getId())).thenReturn(Optional.of(subject));
//        subjectService.delete(subject.getId());
//
//        //then
//        verify(subjectRepository, times(1)).findById(subject.getId());
//        verify(subjectRepository, times(1)).delete(subject);
//    }
//
//    @Test
//    void getAllByOrderByName() {
//        //given
//        List<Subject> subjects = List.of(
//                new Subject(),
//                new Subject(),
//                new Subject()
//        );
//
//        subjectService.create(subjects.get(0));
//        subjectService.create(subjects.get(1));
//        subjectService.create(subjects.get(2));
//
//        //when
//        when(subjectRepository.findAllByOrderByName()).thenReturn(subjects);
//        List<Subject> actual = subjectService.getAllByOrderByName();
//
//        //then
//        assertThat(actual).isEqualTo(subjects);
//    }
//
//    @Test
//    void GetNoneByOrderByName() {
//        //given
//        List<Subject> empty = new ArrayList<>();
//
//        //when
//        List<Subject> actual = subjectService.getAllByOrderByName();
//
//        //then
//        assertThat(actual).isEqualTo(empty);
//    }
//
//
//    @Test
//    void findByName() {
//        //given
//        String name = "Math";
//        Subject subject1 = new Subject(name);
//        Subject subject2 = new Subject("Philosophy");
//
//
//        subjectService.create(subject1);
//        subjectService.create(subject2);
//
//        //when
//        when(subjectRepository.findByName(name)).thenReturn(Optional.of(subject1));
//        Subject actual = subjectService.findByName(name);
//
//        //then
//        assertThat(actual).isEqualTo(subject1);
//    }
//
//    @Test
//    void notFindByName() {
//        //given
//        String name = "Math";
//        Subject subject1 = new Subject(name);
//        Subject subject2 = new Subject("Philosophy");
//
//        subjectService.create(subject1);
//        subjectService.create(subject2);
//
//        //then
//        assertThrowsExactly(IllegalArgumentException.class, () -> subjectService.findByName("---"));
//    }
//
//    @Test
//    void findByTeacher_Id() {
//        //given
//        Teacher teacher = new Teacher();
//        Teacher anotherTeacher = new Teacher();
//
//        List<Subject> teacherSubjects = List.of(
//                new Subject("Algebra", teacher),
//                new Subject("Geometry", teacher)
//        );
//        Subject anotherTeacherSubject = new Subject("Philosophy", anotherTeacher);
//
//        subjectService.create(teacherSubjects.get(0));
//        subjectService.create(teacherSubjects.get(1));
//        subjectService.create(anotherTeacherSubject);
//
//        //when
//        when(subjectRepository.findByTeacher_Id(teacher.getId())).thenReturn(teacherSubjects);
//        List<Subject> actual = subjectService.findByTeacher_Id(teacher.getId());
//
//        //then
//        assertThat(actual).isEqualTo(teacherSubjects);
//    }
//
//    @Test
//    void notFindByTeacher_Id() {
//        //given
//        Subject subject1 = new Subject("Math");
//        Subject subject2 = new Subject("Philosophy");
//
//        subjectService.create(subject1);
//        subjectService.create(subject2);
//
//        //then
//        assertThrowsExactly(IllegalArgumentException.class, () -> subjectService.findByTeacher_Id(-1));
//    }
//
//    @Test
//    void findByStudent_Id() {
//        //given
//        Student student = new Student();
//        Student anotherStudent = new Student();
//
//        List<Subject> studentSubjects = List.of(
//                new Subject("Algebra"),
//                new Subject("Geometry")
//        );
//        studentSubjects.get(0).getStudents().add(student);
//        studentSubjects.get(1).getStudents().add(student);
//
//        Subject anotherStudentSubject = new Subject("Philosophy");
//        anotherStudentSubject.getStudents().add(anotherStudent);
//
//        subjectService.create(studentSubjects.get(0));
//        subjectService.create(studentSubjects.get(1));
//        subjectService.create(anotherStudentSubject);
//
//        //when
//        when(subjectRepository.findByStudent_Id(student.getId())).thenReturn(studentSubjects);
//        List<Subject> actual = subjectService.findByStudent_Id(student.getId());
//
//        //then
//        assertThat(actual).isEqualTo(studentSubjects);
//    }
//
//    @Test
//    void notFindByStudent_Id() {
//        //given
//        Subject subject1 = new Subject("Math");
//        Subject subject2 = new Subject("Philosophy");
//
//        subjectService.create(subject1);
//        subjectService.create(subject2);
//
//        //then
//        assertThrowsExactly(IllegalArgumentException.class, () -> subjectService.findByStudent_Id(-1));
//    }
//}