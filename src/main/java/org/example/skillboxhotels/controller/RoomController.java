package org.example.skillboxhotels.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.skillboxhotels.controller.request.RoomFilter;
import org.example.skillboxhotels.controller.request.RoomRequest;
import org.example.skillboxhotels.controller.response.RoomResponse;
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
@RequestMapping("/api/v1/rooms")
@Validated
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoomResponse getRoomById(@PathVariable Long id) {
        return roomService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse createRoom(@Valid @RequestBody RoomRequest request) {
        return roomService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoomResponse updateRoom(@PathVariable Long id, @Valid @RequestBody RoomRequest request) {
        return roomService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<RoomResponse> getAllRooms(@ParameterObject RoomFilter filter, Pageable pageable) {
        return new PagedModel<>(roomService.findAll(filter, pageable));
    }
}