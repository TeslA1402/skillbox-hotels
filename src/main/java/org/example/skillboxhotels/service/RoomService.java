package org.example.skillboxhotels.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxhotels.controller.request.RoomRequest;
import org.example.skillboxhotels.controller.response.RoomResponse;
import org.example.skillboxhotels.entity.Hotel;
import org.example.skillboxhotels.entity.Room;
import org.example.skillboxhotels.exception.NotFoundException;
import org.example.skillboxhotels.mapper.RoomMapper;
import org.example.skillboxhotels.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelService hotelService;

    @Transactional(readOnly = true)
    public RoomResponse findById(Long id) {
        log.info("Find room by id: {}", id);
        return roomMapper.toRoomResponse(getById(id));
    }

    @Transactional
    public RoomResponse create(RoomRequest request) {
        log.info("Create room: {}", request);
        Hotel hotel = hotelService.getById(request.hotelId());
        Room room = roomMapper.toRoom(request, hotel);
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }

    @Transactional
    public RoomResponse update(Long id, RoomRequest request) {
        log.info("Update room with id: {}. Request: {}", id, request);
        Hotel hotel = hotelService.getById(request.hotelId());
        Room room = getById(id);
        roomMapper.partialUpdate(request, hotel, room);
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Delete room with id: {}", id);
        roomRepository.deleteById(id);
    }

    public Room getById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room not found"));
    }
}
