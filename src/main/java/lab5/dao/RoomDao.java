package lab5.dao;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import lab5.entity.Room;

import org.springframework.stereotype.Repository;

@Repository
public class RoomDao extends GeneralDao<Room> {

    public RoomDao(EntityManager entityManager) {
        super(entityManager, Room.class);
    }

    public Room findByCode(String code) {
        TypedQuery<Room> query = entityManager.createQuery("select from Room where code = :code", Room.class);
        query.setParameter("code", code);
        return query.getSingleResult();
    }
}
