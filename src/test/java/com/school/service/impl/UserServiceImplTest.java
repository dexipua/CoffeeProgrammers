package com.school.service.impl;

import com.school.dto.user.UserRequestUpdate;
import com.school.models.User;
import com.school.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void tryToCreateUser() {
        //given
        User user = new User("Artem", "Moseichenko", "am@gmil.com", "Abekpr257");
        userService.create(user);
        //when
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        //then
        User actualUser = userArgumentCaptor.getValue();
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    void tryToCreateUserWithAlreadyExistName() {
        //given
        User userExist = new User("Artem", "Moseichenko", "am@gmil.com", "Abekpr257");
        when(userRepository.findByEmail(userExist.getEmail()))
                .thenReturn(Optional.of(userExist));

        //when&then
        assertThrows(EntityExistsException.class, () ->
                userService.create(userExist));
    }

    @Test
    void tryToFindByIdWithCorrectInformation() {
        //given
        User user = new User("Artem", "Moseichenko", "am@gmil.com", "Abekpr257");
        userService.create(user);
        // When
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        //then
        User res = userService.findById(1L);
        assertEquals(user, res);
    }

    @Test
    void tryToFindByIdWithWrongInformation() {
        //given
        User user = new User("Artem", "Moseichenko", "am@gmil.com", "Abekpr257");
        userService.create(user);
        //when&then
        assertThrowsExactly(EntityNotFoundException.class, () ->
                userService.findById(2));
    }

    @Test
    void tryToUpdateWithCorrectInformation() {
        //given
        User user = new User("Artem", "Moseichenko", "am@gmil.com", "Abubekir257");
        user.setId(1);
        userService.create(user);
        //when
        User updatedUser = new User("rename", "surname", "Newpassword@gmil.com", "Password441324");
        updatedUser.setId(1);
        UserRequestUpdate userRequestUpdate = new UserRequestUpdate();
        userRequestUpdate.setFirstName("rename");
        userRequestUpdate.setLastName("surname");
        userRequestUpdate.setPassword("Abubekir257");

        when(userRepository.findById(1L)).thenReturn(Optional.of(updatedUser));
        //then
        userService.update(user.getId(), userRequestUpdate);
        User res = userService.findById(1L);
        assertEquals(updatedUser, res);
    }


    @Test
    void tryToFindByEmailWithCorrectInformation() {
        //given
        String email = "feee@nnvr.fejf";
        User user = new User("Artem", "Moseichenko", email, "Abekpr257");
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
        String email = "dexip@nrv.ece";
        User user = new User("Dexip", "Artem", email, "Abekpr257");
        userService.create(user);
        //when&then
        assertThrowsExactly(EntityNotFoundException.class, () -> userService.findByEmail("4ervvrwv"));
    }

    @Test
    void tryToLoadByEmailWithCorrectInformation() {
        //given
        String email = "feee@nnvr.fejf";
        User user = new User("Artem", "Moseichenko", email, "Abekpr257");
        userService.create(user);
        // When
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        //then
        UserDetails result = userService.loadUserByUsername(email);
        assertEquals(user, result);
    }

    @Test
    void tryToLoadByEmailWithWrongInformation() {
        //given
        String email = "feee@nnvr.fejf";
        User user = new User("Artem", "Moseichenko", email, "Abekpr257");
        userService.create(user);
        //when&then
        assertThrowsExactly(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername("rgfr"));
    }
}