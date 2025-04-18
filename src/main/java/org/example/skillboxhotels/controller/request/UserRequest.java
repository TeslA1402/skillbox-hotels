package org.example.skillboxhotels.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for {@link org.example.skillboxhotels.entity.User}
 */
public record UserRequest(@NotBlank String username, @NotBlank String password, @NotBlank @Email String email) {
}