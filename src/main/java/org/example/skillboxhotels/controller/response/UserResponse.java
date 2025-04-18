package org.example.skillboxhotels.controller.response;

import org.example.skillboxhotels.entity.Role;

/**
 * DTO for {@link org.example.skillboxhotels.entity.User}
 */
public record UserResponse(Long id, String username, String email, Role role) {
}