package org.example.skillboxhotels.controller.response;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link org.example.skillboxhotels.entity.Room}
 */
public record RoomResponse(Long id, String name, String description, Integer number, Double price, Integer maxPeople,
                           List<LocalDate> unavailableDates, Long hotelId) {
}