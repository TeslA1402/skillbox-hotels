package org.example.skillboxhotels.repository;

import org.example.skillboxhotels.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}