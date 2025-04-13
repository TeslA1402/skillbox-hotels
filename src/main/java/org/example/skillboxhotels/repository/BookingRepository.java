package org.example.skillboxhotels.repository;

import org.example.skillboxhotels.entity.Booking;
import org.example.skillboxhotels.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b " +
            "WHERE b.room = :room " +
            "AND (:newCheckIn < b.checkOutDate AND :newCheckOut > b.checkInDate)")
    boolean isRoomBooked(@Param("room") Room room,
                         @Param("newCheckIn") LocalDate newCheckIn,
                         @Param("newCheckOut") LocalDate newCheckOut);
}