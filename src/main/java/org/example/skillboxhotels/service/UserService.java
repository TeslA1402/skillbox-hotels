package org.example.skillboxhotels.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxhotels.controller.request.UserRequest;
import org.example.skillboxhotels.controller.response.UserResponse;
import org.example.skillboxhotels.entity.User;
import org.example.skillboxhotels.exception.ConflictException;
import org.example.skillboxhotels.exception.NotFoundException;
import org.example.skillboxhotels.mapper.UserMapper;
import org.example.skillboxhotels.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse create(UserRequest userRequest) {
        log.info("Creating user with username {} and email {}", userRequest.username(), userRequest.email());
        String encodedPassword = encodePassword(userRequest.password());
        User user = userMapper.toUser(userRequest, encodedPassword);
        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new ConflictException("User with the same username or email already exists.");
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse findById(Long id) {
        log.info("Finding user with id {}", id);
        return userMapper.toUserResponse(getById(id));
    }

    private User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        log.info("Retrieving user with username: {}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        log.info("Retrieving all users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }

    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        log.info("Update user with id: {}", id);
        User currentUser = getById(id);
        String encodedPassword = encodePassword(request.password());
        User newUser = userMapper.toUser(request, encodedPassword);
        if (userRepository.existsByUsernameOrEmail(newUser.getUsername(), newUser.getEmail())) {
            throw new ConflictException("User with the same username or email already exists.");
        }
        userMapper.partialUpdate(newUser, currentUser);
        return userMapper.toUserResponse(userRepository.save(currentUser));
    }

    private String encodePassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }
}