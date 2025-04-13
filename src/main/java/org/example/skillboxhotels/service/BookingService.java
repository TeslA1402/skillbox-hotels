package org.example.skillboxhotels.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxhotels.controller.request.BookingRequest;
import org.example.skillboxhotels.controller.response.BookingResponse;
import org.example.skillboxhotels.entity.Booking;
import org.example.skillboxhotels.entity.Room;
import org.example.skillboxhotels.exception.BadRequestException;
import org.example.skillboxhotels.mapper.BookingMapper;
import org.example.skillboxhotels.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final BookingMapper bookingMapper;
    private final UserService userService;

    @Transactional
    public BookingResponse create(BookingRequest request, Long userId) {
        Room room = roomService.getById(request.roomId());
        if (bookingRepository.isRoomBooked(room, request.checkInDate(), request.checkOutDate())) {
            throw new BadRequestException("Room is booked");
        }
        Booking booking = bookingRepository.save(bookingMapper.toBooking(request, room, userService.getById(userId)));
        return bookingMapper.toBookingResponse(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> findAll() {
        return bookingRepository.findAll().stream().map(bookingMapper::toBookingResponse).toList();
    }
}
