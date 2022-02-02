package lab5.service;


import java.util.List;

import lab5.dao.RoomDao;
import lab5.entity.Room;

import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomDao roomDao;
    private final FieldService fieldService;

    public RoomService(RoomDao roomDao, FieldService fieldService) {
        this.roomDao = roomDao;
        this.fieldService = fieldService;
    }

    public Room createNewRoom(String code) {
        Room room = new Room();
        room.setCode(code);
        roomDao.save(room);
        Room savedRoom = roomDao.findByCode(code);
        savedRoom.setFields(List.of(fieldService.createNewField(room), fieldService.createNewField(room)));
        return roomDao.update(savedRoom);
    }
}
