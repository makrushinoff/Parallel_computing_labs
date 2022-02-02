package lab5.service;

import java.util.ArrayList;
import java.util.List;

import lab5.dao.CellDao;
import lab5.entity.Cell;
import lab5.entity.Field;

import org.springframework.stereotype.Service;

@Service
public class CellService {

    private final CellDao cellDao;

    public CellService(CellDao cellDao) {
        this.cellDao = cellDao;
    }

    public List<Cell> createListOfCells(Field field) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            Cell cell = new Cell();
            cell.setField(field);
            cells.add(cellDao.save(cell));
        }
        return cells;
    }

}
