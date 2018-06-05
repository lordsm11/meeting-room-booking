package com.devakt.mapper;

import com.devakt.entity.Room;
import com.devakt.modal.RoomView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomView roomToRoomView(Room room);

}
