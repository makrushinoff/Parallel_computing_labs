package lab5.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GeneralDao<T> {

    protected final EntityManager entityManager;
    private final Class<T> entityClass;

    public GeneralDao(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public List<T> findAll() {
        CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(entityClass);
        return entityManager.createQuery(query).getResultList();
    }

    public Optional<T> findById(Long id) {
        return Optional.of(entityManager.find(entityClass, id));
    }

    public T save(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void deleteById(Long id) {
        entityManager.remove(findById(id).orElseThrow());
    }

}
