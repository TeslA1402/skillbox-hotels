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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final StatService statService;

    @Transactional
    public BookingResponse create(BookingRequest request, Long userId) {
        Room room = roomService.getById(request.roomId());
        if (bookingRepository.isRoomBooked(room, request.checkInDate(), request.checkOutDate())) {
            throw new BadRequestException("Room is booked");
        }
        Booking booking = bookingRepository.save(bookingMapper.toBooking(request, room, userService.getById(userId)));
        statService.sendRoomBooked(booking);
        return bookingMapper.toBookingResponse(booking);
    }

    @Transactional(readOnly = true)
    public Page<BookingResponse> findAll(Pageable pageable) {
        return bookingRepository.findAll(pageable).map(bookingMapper::toBookingResponse);
    }
}
