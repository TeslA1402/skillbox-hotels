package org.example.skillboxhotels.controller.response;

import java.util.List;

/**
 * DTO for {@link org.example.skillboxhotels.entity.Room}
 */
public record RoomResponse(Long id, String name, String description, Integer number, Double price, Integer maxPeople,
                           List<RoomBookingResponse> bookings, Long hotelId) {
}