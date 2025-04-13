package org.example.skillboxhotels.controller.response;

import java.time.LocalDate;

/**
 * DTO for {@link org.example.skillboxhotels.entity.Booking}
 */
public record BookingResponse(Long id, LocalDate checkInDate, LocalDate checkOutDate, Long roomId, Long userId) {
}