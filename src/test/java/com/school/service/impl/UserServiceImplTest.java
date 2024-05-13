package com.school.service.impl;

import com.school.models.User;
import com.school.repositories.UserRepository;
import com.school.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

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
    void create() {
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");

        userService.create(user);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User actualUser = userArgumentCaptor.getValue();

        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    void readById() {
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userService.create(user);
        // When
        when(userRepository.findById(1l)).thenReturn(Optional.of(user));

        User res = userService.readById(1l);
        assertEquals(user, res);
    }

    @Disabled
    @Test
    void update() {
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.update(user);

        assertEquals(user, updatedUser);
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void delete() {
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userService.create(user);

        when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        userService.delete(1l);

        verify(userRepository, times(1)).findById(1l);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void getAll() {
        userService.getAll();

        verify(userRepository).findAll();
    }

    @Test
    void findByUsername() {
        String username = "Dexip";
        User user = new User(username, "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userService.create(user);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = userService.findByUsername(username);

        assertEquals(user, result);
    }

    @Test
    void findByEmail() {
        String email = "feee@nnvr.fejf";
        User user = new User("Dexip", "Artem", "Moseichenko", "Abekpr257", "feee@nnvr.fejf");
        userService.create(user);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.findByEmail(email);

        assertEquals(user, result);
    }
}