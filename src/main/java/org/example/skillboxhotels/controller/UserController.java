package org.example.skillboxhotels.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.skillboxhotels.controller.request.UserRequest;
import org.example.skillboxhotels.controller.response.UserResponse;
import org.example.skillboxhotels.entity.User;
import org.example.skillboxhotels.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@AuthenticationPrincipal User user) {
        return userService.findById(user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirements
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        return userService.create(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateUser(@AuthenticationPrincipal User user, @Valid @RequestBody UserRequest request) {
        return userService.update(user.getId(), request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@AuthenticationPrincipal User user) {
        userService.delete(user.getId());
    }
}