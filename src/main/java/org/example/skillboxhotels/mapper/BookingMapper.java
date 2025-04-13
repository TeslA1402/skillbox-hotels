package org.example.skillboxhotels.mapper;

import org.example.skillboxhotels.controller.request.BookingRequest;
import org.example.skillboxhotels.controller.response.BookingResponse;
import org.example.skillboxhotels.controller.response.RoomBookingResponse;
import org.example.skillboxhotels.entity.Booking;
import org.example.skillboxhotels.entity.Room;
import org.example.skillboxhotels.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookingMapper {
    @Mapping(target = "id", ignore = true)
    Booking toBooking(BookingRequest bookingRequest, Room room, User user);

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "userId", source = "user.id")
    BookingResponse toBookingResponse(Booking booking);
    
    RoomBookingResponse toRoomBookingResponse(Booking booking);
}