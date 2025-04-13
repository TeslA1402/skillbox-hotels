package org.example.skillboxhotels.repository;

import org.example.skillboxhotels.entity.Hotel;
import org.example.skillboxhotels.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByHotel(Hotel hotel);
}