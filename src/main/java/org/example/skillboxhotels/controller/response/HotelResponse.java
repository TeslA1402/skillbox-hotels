package org.example.skillboxhotels.controller.response;

import org.example.skillboxhotels.entity.Hotel;

/**
 * DTO for {@link Hotel}
 */
public record HotelResponse(Long id, String name, String title, String city, String address, Integer distanceFromCenter,
                            Double rating, int numberOfRatings) {
}