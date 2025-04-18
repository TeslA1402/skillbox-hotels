package org.example.skillboxhotels.mapper;

import org.example.skillboxhotels.controller.request.HotelRequest;
import org.example.skillboxhotels.controller.response.HotelResponse;
import org.example.skillboxhotels.entity.Hotel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface HotelMapper extends CommonMapper {
    HotelResponse toHotelResponse(Hotel hotel);

    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "numberOfRatings", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", qualifiedByName = "trim")
    @Mapping(target = "title", qualifiedByName = "trim")
    @Mapping(target = "city", qualifiedByName = "trim")
    @Mapping(target = "address", qualifiedByName = "trim")
    Hotel toHotel(HotelRequest hotelRequest);

    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "numberOfRatings", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", qualifiedByName = "trim")
    @Mapping(target = "title", qualifiedByName = "trim")
    @Mapping(target = "city", qualifiedByName = "trim")
    @Mapping(target = "address", qualifiedByName = "trim")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(HotelRequest hotelRequest, @MappingTarget Hotel hotel);
}