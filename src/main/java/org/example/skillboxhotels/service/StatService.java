package org.example.skillboxhotels.service;

import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.example.skillboxhotels.entity.Booking;
import org.example.skillboxhotels.entity.StatRecord;
import org.example.skillboxhotels.entity.User;
import org.example.skillboxhotels.kafka.StatKafkaProducer;
import org.example.skillboxhotels.repository.StatRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class StatService {
    private final StatKafkaProducer statKafkaProducer;
    private final StatRecordRepository statRepository;

    public void sendUserRegistered(User user) {
        statKafkaProducer.sendUserRegistered(user.getId());
    }

    public void sendRoomBooked(Booking booking) {
        statKafkaProducer.sendRoomBooked(booking.getUser().getId(), booking.getCheckInDate(), booking.getCheckOutDate());
    }

    public void save(StatRecord statRecord) {
        statRepository.save(statRecord);
    }

    @Transactional(readOnly = true)
    @SneakyThrows
    public void exportStatsToCsv(ServletOutputStream outputStream) {
        log.info("Export stats to csv");
        List<StatRecord> stats = statRepository.findAll();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.builder().setHeader("id", "type", "timestamp", "data").get())) {
            for (StatRecord statRecord : stats) {
                printer.printRecord(
                        statRecord.id(),
                        statRecord.type(),
                        statRecord.timestamp(),
                        statRecord.data()
                );
            }
        }
    }
}
