package com.school.repositories;

import com.school.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        //given
        User user = new User("Artem", "Moseichenko",  "am@gmil.com","Abekpr257");
        userRepository.save(user);

        //when
        User res = userRepository.findByEmail("am@gmil.com").get();
        boolean result = res.equals(user);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void NotFindByEmail() {
        //given
        User user = new User("Artem", "Moseichenko",  "am@gmil.com","Abekpr257");
        userRepository.save(user);

        //then
        Optional<User> res = userRepository.findByEmail("efsevesf");
        assertThrows(NoSuchElementException.class, () -> {
            User value = res.get();
        });

    }
}