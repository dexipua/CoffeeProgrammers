package com.school.repositories;

import com.school.models.Subject;
import com.school.models.Teacher;
import com.school.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;


    @AfterEach
    void tearDown() {
        teacherRepository.deleteAll();
    }

    @Test
    void findBySubjectName() {
        //given
        Subject mathSubject = new Subject("Mathematics");
        Subject artSubject = new Subject("Art");

        Teacher teacher = new Teacher(new User("Artem", "Moseichenko",  "am@gmil.com","Abekpr257"));
        mathSubject.setTeacher(teacher);
        artSubject.setTeacher(teacher);

        mathSubject.setTeacher(teacher);

        subjectRepository.saveAll(List.of(mathSubject, artSubject));
        teacherRepository.save(teacher);

        //when
        List<Teacher> res = teacherRepository.findBySubjectNameIgnoreCase("Mathematics");

        //then
        assertEquals(res, List.of(teacher));
    }

    @Test
    void notFindBySubjectName() {
        //given
        Subject mathSubject = new Subject("Mathematics");
        Subject artSubject = new Subject("Art");

        Teacher teacher = new Teacher(new User("Artem", "Moseichenko",  "am@gmil.com","Abekpr257"));
        mathSubject.setTeacher(teacher);

        mathSubject.setTeacher(teacher);

        subjectRepository.saveAll(List.of(mathSubject, artSubject));
        teacherRepository.save(teacher);

        //then
        List<Teacher> res = teacherRepository.findBySubjectNameIgnoreCase("Art");
        assertEquals(0, res.size());
    }

    @Test
    void findByUserId() {
        //given
        Teacher teacher = new Teacher(new User("Artem", "Moseichenko",  "am@gmil.com","Abekpr257"));
        teacherRepository.save(teacher);

        //when
        Optional<Teacher> res = teacherRepository.findById(teacher.getId());
        boolean result = res.get().equals(teacher);

        //then
        assertThat(result).isTrue();
    }


    @Test
    void notFindByUserId() {
        //given
        Teacher teacher = new Teacher(new User("Artem", "Moseichenko",  "am@gmil.com","Abekpr257"));
        teacherRepository.save(teacher);

        //then
        Optional<Teacher> res = teacherRepository.findById((long) -1);
        assertThrows(NoSuchElementException.class, res::get);
    }
}
