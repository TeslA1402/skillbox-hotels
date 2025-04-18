package org.example.skillboxhotels.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.skillboxhotels.entity.Hotel;

/**
 * DTO for {@link Hotel}
 */
public record HotelRequest(@NotBlank String name, @NotBlank String title, @NotBlank String city,
                           @NotBlank String address, @NotNull @PositiveOrZero Integer distanceFromCenter) {
}