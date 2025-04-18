package org.example.skillboxhotels.entity;

import org.example.skillboxhotels.event.Event;
import org.example.skillboxhotels.event.EventType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "statistics")
public record StatRecord(
        @Id String id,
        EventType type,
        LocalDateTime timestamp,
        Event data
) {
}
