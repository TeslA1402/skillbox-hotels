package org.example.skillboxhotels.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxhotels.event.RoomBookedEvent;
import org.example.skillboxhotels.event.UserRegisteredEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatKafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserRegistered(Long userId) {
        log.info("Sending user registered event for user id: {}", userId);
        kafkaTemplate.send("user-registered", new UserRegisteredEvent(userId));
    }

    public void sendRoomBooked(Long userId, LocalDate in, LocalDate out) {
        log.info("Sending room booked event for user id: {}, in: {}, out: {}", userId, in, out);
        kafkaTemplate.send("room-booked", new RoomBookedEvent(userId, in, out));
    }
}
