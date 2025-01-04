package org.example.skillboxhotels.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO for {@link org.example.skillboxhotels.entity.Room}
 */
public record RoomRequest(@NotBlank String name, @NotBlank String description, @Positive Integer number,
                          @Positive Double price, @Positive Integer maxPeople, @NotNull Long hotelId) {
}