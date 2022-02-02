package lab5.service;

import lab5.dao.FieldDao;
import lab5.entity.Field;
import lab5.entity.Room;

import org.springframework.stereotype.Service;

@Service
public class FieldService {

    private final CellService cellService;
    private final FieldDao fieldDao;

    public FieldService(CellService cellService, FieldDao fieldDao) {
        this.cellService = cellService;
        this.fieldDao = fieldDao;
    }

    public Field createNewField(Room room) {
        Field field = new Field();
        field.setRoom(room);
        Field savedField = fieldDao.save(field);
        savedField.setCells(cellService.createListOfCells(field));
        return fieldDao.update(savedField);
    }
}
