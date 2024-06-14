package com.school.repositories;

import com.school.models.Student;
import com.school.models.User;
import com.school.models.UserNews;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserNewsRepositoryTest {

    @Autowired
    private UserNewsRepository userNewsRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void findAllByUserId() throws InterruptedException {
        Student student = new Student(new User("Id", "Od", "dhjgfjdhj@gmail.com", "Sopgjdhjdgfhjgf2"));
        studentRepository.save(student);
        UserNews userNews1 = new UserNews("New mark 10", student.getUser());
        Thread.sleep(10);
        UserNews userNews2 = new UserNews("New mark 3", student.getUser());
        userNewsRepository.saveAll(List.of(userNews2, userNews1));

        assertEquals(List.of(userNews2, userNews1), userNewsRepository.findAllByUser_IdOrderByTimeDesc(student.getUser().getId()));
    }
}
