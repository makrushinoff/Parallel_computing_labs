package lab5.dao;

import javax.persistence.EntityManager;

import lab5.entity.Field;

import org.springframework.stereotype.Repository;

@Repository
public class FieldDao extends GeneralDao<Field> {

    public FieldDao(EntityManager entityManager, Class<Field> entityClass) {
        super(entityManager, entityClass);
    }
}
