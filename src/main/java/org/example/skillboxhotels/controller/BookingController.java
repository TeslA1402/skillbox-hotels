package org.example.skillboxhotels.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.skillboxhotels.controller.request.BookingRequest;
import org.example.skillboxhotels.controller.response.BookingResponse;
import org.example.skillboxhotels.entity.User;
import org.example.skillboxhotels.service.BookingService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookings")
@Validated
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<BookingResponse> getAllBookings(Pageable pageable) {
        return new PagedModel<>(bookingService.findAll(pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse createBooking(@Valid @RequestBody BookingRequest request, @AuthenticationPrincipal User user) {
        return bookingService.create(request, user.getId());
    }

}
