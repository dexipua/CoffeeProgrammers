package com.school.service.impl;

import com.school.models.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import com.school.repositories.UserRepository;
import com.school.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        if (user != null) {
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
            return userRepository.save(user);
        }
        throw new EntityNotFoundException("User with id " + user.getId() + " not found");
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
        User user = userRepository.findByEmail(username).get();
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        return user;
    }

    public User findByEmail(String email){
        User user = userRepository.findByEmail(email).get();
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        return user;
    }
}
