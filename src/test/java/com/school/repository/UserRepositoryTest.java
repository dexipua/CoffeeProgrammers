package com.school.repository;

import com.school.models.Role;
import com.school.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() {
        //given
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userRepository.save(user);

        //when
        User res = userRepository.findByEmail("feee@nnvr.fejf").get();
        boolean result = res.equals(user);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void findByUsername() {
        //given
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userRepository.save(user);

        //when
        User res = userRepository.findByUsername("Dexip").get();
        boolean result = res.equals(user);

        //then
        assertThat(result).isTrue();
    }
}