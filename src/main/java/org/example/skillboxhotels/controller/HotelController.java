package org.example.skillboxhotels.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.skillboxhotels.controller.request.HotelFilter;
import org.example.skillboxhotels.controller.request.HotelRequest;
import org.example.skillboxhotels.controller.request.RateRequest;
import org.example.skillboxhotels.controller.response.HotelResponse;
import org.example.skillboxhotels.service.HotelService;
import org.example.skillboxhotels.service.RoomService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotels")
@Validated
public class HotelController {
    private final HotelService hotelService;
    private final RoomService roomService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<HotelResponse> getAllHotels(@ParameterObject HotelFilter filter, Pageable pageable) {
        return new PagedModel<>(hotelService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponse getHotelById(@PathVariable Long id) {
        return hotelService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponse createHotel(@Valid @RequestBody HotelRequest request) {
        return hotelService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponse updateHotel(@PathVariable Long id, @Valid @RequestBody HotelRequest request) {
        return hotelService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable Long id) {
        hotelService.delete(id);
    }

    @PostMapping("/{id}/rate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rateHotel(@PathVariable Long id, @Valid @RequestBody RateRequest request) {
        hotelService.rate(id, request);
    }
}