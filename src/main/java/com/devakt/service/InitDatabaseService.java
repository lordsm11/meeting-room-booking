package com.devakt.service;

import com.devakt.entity.User;
import com.devakt.entity.Room;
import com.devakt.repository.BookingRepository;
import com.devakt.repository.UserRepository;
import com.devakt.repository.RoomRepository;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class InitDatabaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitDatabaseService.class);

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public void initData() {
        RoomsData roomsData = retrieveDataFromConfig();

        roomRepository.deleteAll();
        roomRepository.saveAll(roomsData.getRooms());

        userRepository.deleteAll();
        userRepository.saveAll(roomsData.getUsers());

        bookingRepository.deleteAll();

    }

    private RoomsData retrieveDataFromConfig() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("src/main/resources/data/data.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonData, RoomsData.class);
        } catch (Exception e) {
            LOGGER.error("error on json serialization", e);
            return new RoomsData();
        }
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @AllArgsConstructor
    static class RoomsData {
        List<Room> rooms;
        List<User> users;
    }

}


