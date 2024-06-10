package com.school.service.impl;

import com.school.dto.mark.MarkRequest;
import com.school.models.Mark;
import com.school.models.Student;
import com.school.models.Subject;
import com.school.models.User;
import com.school.repositories.MarkRepository;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MarkServiceImplTest {
    @Mock
    private MarkRepository markRepository;
    @Mock
    private SubjectService subjectService;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private MarkServiceImpl markService;

    private MarkRequest markRequest;
    private Student student1;
    private Student student2;
    private Subject subject1;
    private Subject subject2;
    private Mark expected;
    private Mark mark1;
    private Mark mark2;

    @BeforeEach
    void setUp() {
        markRequest = new MarkRequest();
        markRequest.setValue(10);

        student1 = new Student(new User("Vlad", "Bulakovskyi", "vlad1@gmail.com", "Vlad123"));
        student1.setId(1);

        student2 = new Student(new User("Vlad", "Bulakovskyi", "vlad13@gmail.com", "Vlad123"));
        student2.setId(2);

        subject1 = new Subject();
        subject1.setName("Math");
        subject1.setId(1);
        subject1.setStudents(new ArrayList<>());

        subject2 = new Subject();
        subject2.setName("Art");
        subject2.setId(2);
        subject2.setStudents(new ArrayList<>());

        expected = MarkRequest.toMark(markRequest);
        expected.setValue(10);
        expected.setId(1);
        expected.setStudent(student1);
        expected.setSubject(subject1);

        mark1 = new Mark();
        mark1.setValue(10);
        mark1.setId(1);

        mark2 = new Mark();
        mark2.setValue(2);
        mark2.setId(2);
    }
    @Test
    void create() {
        // given
        subject1.setStudents(new ArrayList<>(List.of(student1)));

        // when
        when(subjectService.findById(subject1.getId())).thenReturn(subject1);
        when(studentService.findById(student1.getId())).thenReturn(student1);
        when(markRepository.save(any(Mark.class))).thenReturn(expected);

        // then
        markService.create(markRequest, subject1.getId(), student1.getId());
        ArgumentCaptor<Mark> markArgumentCaptor = ArgumentCaptor.forClass(Mark.class);
        verify(markRepository).save(markArgumentCaptor.capture());

        verify(studentService, times(2)).findById(student1.getId());
        verify(subjectService, times(2)).findById(subject1.getId());

        Mark capturedMark = markArgumentCaptor.getValue();
        assertEquals(student1, capturedMark.getStudent());
        assertEquals(subject1, capturedMark.getSubject());
        assertEquals(markRequest.getValue(), capturedMark.getValue());
    }

    @Test
    void createStudentDontHaveSubject() {
        // when
        when(subjectService.findById(subject1.getId())).thenReturn(subject1);
        when(studentService.findById(student1.getId())).thenReturn(student1);

        // then
        assertThrows(UnsupportedOperationException.class, () -> markService.create(markRequest, subject1.getId(), student1.getId()));

        verify(studentService, times(1)).findById(student1.getId());
        verify(subjectService, times(1)).findById(subject1.getId());
    }

    @Test
    void findById() {
        // when
        when(markRepository.findById(mark1.getId())).thenReturn(Optional.of(mark1));

        // then
        assertEquals(markService.findById(mark1.getId()), mark1);
        verify(markRepository, times(1)).findById(mark1.getId());
    }

    @Test
    void notFindById() {
        // then
        assertThrows(EntityNotFoundException.class, () -> markService.findById(-1));
        verify(markRepository, times(1)).findById(-1);
    }

    @Test
    void update() {
        // when
        when(markRepository.findById(mark1.getId())).thenReturn(Optional.of(mark1));
        when(markRepository.save(any(Mark.class))).thenReturn(expected);

        // then
        markService.update(mark1.getId(), markRequest);
        ArgumentCaptor<Mark> markArgumentCaptor = ArgumentCaptor.forClass(Mark.class);
        verify(markRepository, times(1)).save(markArgumentCaptor.capture());

        verify(markRepository, times(1)).findById(mark1.getId());

        Mark capturedMark = markArgumentCaptor.getValue();
        assertEquals(markRequest.getValue(), capturedMark.getValue());
    }

    @Test
    void delete() {
        // when
        when(markRepository.findById(mark1.getId())).thenReturn(Optional.of(mark1));

        // then
        markService.delete(mark1.getId());

        verify(markRepository, times(1)).findById(mark1.getId());
        verify(markRepository, times(1)).delete(mark1);
    }

    @Test
    void findAllByStudentId() {
        // given
        subject1.setStudents(List.of(student1));

        subject2.setStudents(List.of(student1));

        mark1.setStudent(student1);
        mark1.setSubject(subject1);

        mark2.setStudent(student1);
        mark2.setSubject(subject2);

        // when
        when(markRepository.findAllByStudent_Id(student1.getId())).thenReturn(List.of(mark1, mark2));

        // then
        assertEquals(markService.findAllByStudentId(student1.getId()), Map.of(subject1, List.of(mark1), subject2, List.of(mark2)));

        verify(markRepository, times(1)).findAllByStudent_Id(student1.getId());
    }

    @Test
    void findAllBySubjectId() {
        // given
        subject1.setStudents(List.of(student1, student2));

        mark1.setStudent(student1);
        mark1.setSubject(subject1);

        mark2.setStudent(student2);
        mark2.setSubject(subject1);

        // when
        when(markRepository.findAllBySubject_Id(subject1.getId())).thenReturn(List.of(mark1, mark2));

        // then
        assertEquals(markService.findAllBySubjectId(subject1.getId()), Map.of(student1, List.of(mark1), student2, List.of(mark2)));

        verify(markRepository, times(1)).findAllBySubject_Id(subject1.getId());
    }

    @Test
    void findAverageByStudentId() {
        // given
        subject1.setStudents(List.of(student1));

        subject2.setStudents(List.of(student1));

        mark1.setStudent(student1);
        mark1.setSubject(subject1);

        mark2.setStudent(student1);
        mark2.setSubject(subject2);

        // when
        when(markRepository.findAllByStudent_Id(student1.getId())).thenReturn(List.of(mark1, mark2));
        when(studentService.findById(student1.getId())).thenReturn(student1);

        // then
        assertEquals(markService.findAverageByStudentId(student1.getId()), 6);

        verify(markRepository, times(1)).findAllByStudent_Id(student1.getId());
        verify(studentService, times(1)).findById(student1.getId());
    }
}
