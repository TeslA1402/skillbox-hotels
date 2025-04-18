package org.example.skillboxhotels.controller.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO for {@link org.example.skillboxhotels.entity.Booking}
 */
public record BookingRequest(@NotNull LocalDate checkInDate, @NotNull LocalDate checkOutDate,
                             @NotNull Long roomId) {
}