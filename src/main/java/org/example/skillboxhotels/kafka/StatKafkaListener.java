package org.example.skillboxhotels.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxhotels.entity.StatRecord;
import org.example.skillboxhotels.event.Event;
import org.example.skillboxhotels.service.StatService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatKafkaListener {
    private final StatService statService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = {"user-registered", "room-booked"}, groupId = "stat-service")
    @SneakyThrows
    public void listen(Event event) {
        log.info("Received event: {}", event);
        StatRecord statRecord = new StatRecord(
                null,
                event.type(),
                LocalDateTime.now(),
                event
        );
        statService.save(statRecord);
    }
}
