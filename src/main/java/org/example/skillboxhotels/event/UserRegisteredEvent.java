package org.example.skillboxhotels.event;

public record UserRegisteredEvent(Long userId) implements Event {
    public EventType type() {
        return EventType.USER_REGISTERED;
    }
}