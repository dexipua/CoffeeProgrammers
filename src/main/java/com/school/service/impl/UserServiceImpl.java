package com.school.service.impl;

import com.school.models.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.school.repositories.UserRepository;
import com.school.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        if (user != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new EntityNotFoundException("User not found");
    }

    @Override
    public User readById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User update(User user) {
        if (user != null) {
            readById(user.getId());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new EntityNotFoundException("User is null");
    }

    @Override
    public void delete(long id) {
        User user = readById(id);
        userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if (!(user.isPresent())) {
            throw new EntityNotFoundException("User not found");
        }
        return user.get();
    }

    public User findByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if (!(user.isPresent())) {
            throw new EntityNotFoundException("User not found");
        }
        return user.get();
    }
}

