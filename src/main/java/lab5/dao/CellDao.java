package lab5.dao;

import javax.persistence.EntityManager;

import lab5.entity.Cell;

import org.springframework.stereotype.Repository;

@Repository
public class CellDao extends GeneralDao<Cell> {

    public CellDao(EntityManager entityManager, Class<Cell> entityClass) {
        super(entityManager, entityClass);
    }
}
