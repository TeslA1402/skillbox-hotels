package org.example.skillboxhotels.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxhotels.controller.request.HotelRequest;
import org.example.skillboxhotels.controller.request.RateRequest;
import org.example.skillboxhotels.controller.response.HotelResponse;
import org.example.skillboxhotels.entity.Hotel;
import org.example.skillboxhotels.exception.NotFoundException;
import org.example.skillboxhotels.mapper.HotelMapper;
import org.example.skillboxhotels.repository.HotelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Transactional(readOnly = true)
    public Page<HotelResponse> findAll(Pageable pageable) {
        log.info("Find all hotels");
        return hotelRepository.findAll(pageable).map(hotelMapper::toHotelResponse);
    }

    @Transactional(readOnly = true)
    public HotelResponse findById(Long id) {
        log.info("Find hotel by id: {}", id);
        return hotelMapper.toHotelResponse(getById(id));
    }

    @Transactional
    public HotelResponse create(HotelRequest request) {
        log.info("Create hotel: {}", request);
        Hotel hotel = hotelMapper.toHotel(request);
        return hotelMapper.toHotelResponse(hotelRepository.save(hotel));
    }

    @Transactional
    public HotelResponse update(Long id, HotelRequest request) {
        log.info("Update hotel with id: {}. Request: {}", id, request);
        Hotel hotel = getById(id);
        hotelMapper.partialUpdate(request, hotel);
        return hotelMapper.toHotelResponse(hotelRepository.save(hotel));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Delete hotel with id: {}", id);
        hotelRepository.deleteById(id);
    }

    public Hotel getById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new NotFoundException("Hotel not found"));
    }

    @Transactional
    public void rate(Long hotelId, RateRequest request) {
        log.info("Rate hotel with id: {}. Request: {}", hotelId, request);
        Integer newMark = request.newMark();
        Hotel hotel = getById(hotelId);
        double rating = Objects.requireNonNullElse(hotel.getRating(), 0.0);
        int numberOfRatings = hotel.getNumberOfRatings();
        if (numberOfRatings == 0) {
            rating = newMark.doubleValue();
        } else {
            double totalRating = rating * numberOfRatings;
            totalRating = totalRating - rating + newMark;
            rating = Math.ceil(totalRating / numberOfRatings * 10) / 10;
        }
        numberOfRatings++;
        hotel.setRating(rating);
        hotel.setNumberOfRatings(numberOfRatings);
    }
}
