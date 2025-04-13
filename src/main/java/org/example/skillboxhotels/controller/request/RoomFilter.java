package org.example.skillboxhotels.controller.request;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.example.skillboxhotels.entity.Booking;
import org.example.skillboxhotels.entity.Hotel;
import org.example.skillboxhotels.entity.Room;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public record RoomFilter(Long id, String name, Double minPrice, Double maxPrice, Integer people,
                         LocalDate checkInDate, LocalDate checkOutDate, Long hotelId) {

    public Specification<Room> getSpecification() {
        return Specification.where(byId())
                .and(byName())
                .and(byPrice())
                .and(byPeople())
                .and(byCheckDate())
                .and(byHotelId());
    }

    private Specification<Room> byId() {
        return id == null ? null : (root, query, cb) -> cb.equal(root.get(Room.Fields.id.name()), id);
    }

    private Specification<Room> byName() {
        return name == null ? null : (root, query, cb) -> cb.like(cb.upper(root.get(Room.Fields.name.name())), "%" + name.toUpperCase() + "%");
    }

    private Specification<Room> byPrice() {
        return minPrice == null && maxPrice == null ? null : (root, query, cb) -> {
            if (maxPrice == null) {
                return cb.greaterThanOrEqualTo(root.get(Room.Fields.price.name()), minPrice);
            } else if (minPrice == null) {
                return cb.lessThanOrEqualTo(root.get(Room.Fields.price.name()), maxPrice);
            } else {
                return cb.between(root.get(Room.Fields.price.name()), minPrice, maxPrice);
            }
        };
    }

    private Specification<Room> byPeople() {
        return people == null ? null : (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Room.Fields.maxPeople.name()), people);
    }

    private Specification<Room> byCheckDate() {
        return checkInDate == null || checkOutDate == null ? null : (root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Booking> booking = subquery.from(Booking.class);
            subquery.select(booking.get(Booking.Fields.room.name()).get(Booking.Fields.id.name()))
                    .where(cb.and(
                            cb.lessThan(booking.get("checkInDate"), checkOutDate),
                            cb.greaterThan(booking.get("checkOutDate"), checkInDate)
                    ));
            return cb.not(root.get(Room.Fields.id.name()).in(subquery));
        };
    }

    private Specification<Room> byHotelId() {
        return hotelId == null ? null : (root, query, cb) -> cb.equal(root.get(Room.Fields.hotel.name()).get(Hotel.Fields.id.name()), hotelId);
    }
}
