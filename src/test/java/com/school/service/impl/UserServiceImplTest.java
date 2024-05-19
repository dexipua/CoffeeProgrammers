package com.school.service.impl;

import com.school.models.User;
import com.school.repositories.UserRepository;
import com.school.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }



    @Test
    void tryToCreateUser() {
        //given
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userService.create(user);
        //when
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        //then
        User actualUser = userArgumentCaptor.getValue();
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    void tryToCreateUserWithNull() {
        //given
        User user = null;
        //when&then
        assertThrowsExactly(EntityNotFoundException.class, () -> {
            userService.create(user);
        });
    }

    @Test
    void tryToReadByIdWithCorrectInformation() {
        //given
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userService.create(user);
        // When
        when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        //then
        User res = userService.readById(1l);
        assertEquals(user, res);
    }

    @Test
    void tryToReadByIdWithWrongInformation() {
        //given
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userService.create(user);
        //when&then
        assertThrowsExactly(EntityNotFoundException.class, () -> {
            userService.readById(2);
        });
    }

    @Test
    void tryToUpdateWithCorrectInformation() {
        //given
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        // When
        User updatedUser = userService.update(user);
        //then
        assertEquals(user, updatedUser);
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void tryToUpdateWithWrongInformation() {
        //given
        User user = null;
        //when&then
        assertThrowsExactly(EntityNotFoundException.class, () -> {
            userService.update(user);
        });
    }

    @Test
    void tryToDeleteWithCorrectInformation() {
        //given
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userService.create(user);
        // When
        when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        userService.delete(1l);
        //then
        verify(userRepository, times(1)).findById(1l);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void tryToGetAll() {
        //given
        userService.getAll();
        //when&then
        verify(userRepository).findAll();
    }

    @Test
    void tryToFindByUsernameWithCorrectInformation() {
        //given
        String username = "Dexip";
        User user = new User(username, "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userService.create(user);
        // When
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        //then
        User result = userService.findByUsername(username);
        assertEquals(user, result);
    }

    @Test
    void tryToFindByUsernameWithWrongInformation() {
        //given
        String username = "Dexip";
        User user = new User(username, "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userService.create(user);
        //when&then
        assertThrowsExactly(EntityNotFoundException.class, () -> {
            userService.findByUsername("rgfr");
        });
    }

    @Test
    void tryToFindByEmailWithCorrectInformation() {
        //given
        String email = "feee@nnvr.fejf";
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userService.create(user);
        // When
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        //then
        User result = userService.findByEmail(email);
        assertEquals(user, result);
    }

    @Test
    void tryToFindByEmailWithWrongInformation() {
        //given
        String username = "Dexip";
        User user = new User(username, "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userService.create(user);
        //when&then
        assertThrowsExactly(EntityNotFoundException.class, () -> {
            userService.findByEmail("rgfr");
        });
    }
}