package org.example.skillboxhotels.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxhotels.controller.request.UserRequest;
import org.example.skillboxhotels.controller.response.UserResponse;
import org.example.skillboxhotels.entity.Role;
import org.example.skillboxhotels.entity.User;
import org.example.skillboxhotels.exception.ConflictException;
import org.example.skillboxhotels.exception.NotFoundException;
import org.example.skillboxhotels.mapper.UserMapper;
import org.example.skillboxhotels.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse create(UserRequest userRequest) {
        log.info("Creating user with username {} and email {}", userRequest.username(), userRequest.email());
        String encodedPassword = passwordEncoder.encode(userRequest.password());
        User user = userMapper.toUser(userRequest, encodedPassword);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ConflictException("User with the same username already exists.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("User with the same email already exists.");
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        log.info("Finding user with id {}", id);
        return userMapper.toUserResponse(getById(id));
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
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
        String encodedPassword = passwordEncoder.encode(request.password());
        User newUser = userMapper.toUser(request, encodedPassword);

        if (!currentUser.getUsername().equals(newUser.getUsername()) && userRepository.existsByUsername(newUser.getUsername())) {
            throw new ConflictException("User with the same username already exists.");
        }
        if (!currentUser.getEmail().equals(newUser.getEmail()) && userRepository.existsByEmail(newUser.getEmail())) {
            throw new ConflictException("User with the same email already exists.");
        }
        userMapper.partialUpdate(newUser, currentUser);
        return userMapper.toUserResponse(userRepository.save(currentUser));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @PostConstruct
    public void init() {
        log.info("Initializing users");
        if (userRepository.count() == 0) {
            log.info("No users found. Creating default admin user");
            User user = User.builder()
                    .username("admin")
                    .password("$2a$12$M4pph..USQSbgRIXZwsAO.9N6lIcOZehuc.3.Scu9SDpRcTpXXIe2")
                    .email("admin@localhost.localdomain")
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(user);
        }
    }
}