package org.example.skillboxhotels.event;

import java.time.LocalDate;

public record RoomBookedEvent(Long userId, LocalDate checkInDate, LocalDate checkOutDate) implements Event {
    public EventType type() {
        return EventType.ROOM_BOOKED;
    }
}
