package org.example.skillboxhotels.controller.response;

import java.time.LocalDate;

/**
 * DTO for {@link org.example.skillboxhotels.entity.Booking}
 */
public record RoomBookingResponse(LocalDate checkInDate, LocalDate checkOutDate) {
}