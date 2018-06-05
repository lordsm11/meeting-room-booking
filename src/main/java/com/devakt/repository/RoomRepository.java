package com.devakt.repository;

import java.util.Optional;

import com.devakt.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {

    Optional<Room> findById(int roomId);

}