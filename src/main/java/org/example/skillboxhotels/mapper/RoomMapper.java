package org.example.skillboxhotels.mapper;

import org.example.skillboxhotels.controller.request.RoomRequest;
import org.example.skillboxhotels.controller.response.RoomResponse;
import org.example.skillboxhotels.entity.Hotel;
import org.example.skillboxhotels.entity.Room;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = BookingMapper.class)
public interface RoomMapper extends CommonMapper {
    @Mapping(target = "hotelId", source = "hotel.id")
    RoomResponse toRoomResponse(Room room);

    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name", qualifiedByName = "trim")
    @Mapping(target = "description", qualifiedByName = "trim")
    Room toRoom(RoomRequest request, Hotel hotel);

    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name", qualifiedByName = "trim")
    @Mapping(target = "description", qualifiedByName = "trim")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(RoomRequest request, Hotel hotel, @MappingTarget Room room);
}