package com.school.service.impl;

import com.school.models.Subject;
import com.school.repositories.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

    @Mock
    private SubjectRepository subjectRepository;
    private SubjectServiceImpl subjectService;

    @BeforeEach
    void setUp() {
        subjectService = new SubjectServiceImpl(subjectRepository);
    }

    @Test
    void create() {
        //given
        Subject subject = new Subject();

        // when
        subjectService.create(subject);

        //then
        ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectRepository).save(subjectArgumentCaptor.capture());
        Subject capturedSubject = subjectArgumentCaptor.getValue();

        assertThat(capturedSubject).isEqualTo(subject);
    }

    @Test
    void createNull() {
        //given
        Subject subject = null;

        //then
        assertThrowsExactly(EntityNotFoundException.class, () -> subjectService.create(subject));
    }

    @Test
    @Disabled
    void readById() {
        //given
        Subject subject = new Subject();
        subjectService.create(subject);

        // when
        Subject actual = subjectService.readById(subject.getId());

        //then
//        ArgumentCaptor<Subject> subjectArgumentCaptor = ArgumentCaptor.forClass(Subject.class);
//        verify(subjectRepository).save(subjectArgumentCaptor.capture());
//        Subject capturedSubject = subjectArgumentCaptor.getValue();

        assertThat(actual).isEqualTo(subject);
    }

    @Test
    void notReadById() {
        //then
        assertThrowsExactly(EntityNotFoundException.class, () -> subjectService.readById(0L));
    }
    @Test
    @Disabled
    void update() {
    }

    @Test
    @Disabled
    void delete() {
    }

    @Test
    @Disabled
    void getAllByOrderByName() {
    }

    @Test
    @Disabled
    void findByName() {
    }

    @Test
    @Disabled
    void findByTeacher_Id() {
    }

    @Test
    @Disabled
    void findByStudent_Id() {
    }
}