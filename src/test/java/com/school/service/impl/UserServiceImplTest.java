package com.school.service.impl;

import com.school.dto.user.UserRequestUpdate;
import com.school.models.User;
import com.school.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Artem", "Moseichenko", "am@gmil.com", "Abekpr257");
        user.setId(1);
    }

    @Test
    void tryToCreateUser() {
        //then
        userService.create(user);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User actualUser = userArgumentCaptor.getValue();
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    void tryToCreateUserWithAlreadyExistName() {
        //given
        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        //when&then
        assertThrows(EntityExistsException.class, () ->
                userService.create(user));
    }

    @Test
    void tryToFindByIdWithCorrectInformation() {
        // When
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        //then
        User res = userService.findById(user.getId());
        assertEquals(user, res);
    }

    @Test
    void tryToFindByIdWithWrongInformation() {
        assertThrowsExactly(EntityNotFoundException.class, () ->
                userService.findById(-1));
    }

    @Test
    void tryToUpdateWithCorrectInformation() {
        //when
        User updatedUser = new User("Rename", "Surname", "Newpassword@gmil.com", "Password441324");
        updatedUser.setId(1);
        UserRequestUpdate userRequestUpdate = new UserRequestUpdate();
        userRequestUpdate.setFirstName("Rename");
        userRequestUpdate.setLastName("Surname");
        userRequestUpdate.setPassword("Abubekir257");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(updatedUser));
        //then
        userService.update(user.getId(), userRequestUpdate);
        User res = userService.findById(user.getId());
        assertEquals(updatedUser, res);
    }


    @Test
    void tryToFindByEmailWithCorrectInformation() {
        // When
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        //then
        User result = userService.findByEmail(user.getEmail());
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
        // When
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        //then
        UserDetails result = userService.loadUserByUsername(user.getEmail());
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