package dao;

import entity.SuperEntity;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

public abstract class CrudDAOImpl<T extends SuperEntity, ID extends Serializable> implements CrudDAO<T,ID> {
    protected EntityManager entityManager;
    private Class<T> entity;

    public CrudDAOImpl() {
       entity =  (Class<T>)(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    }

    @Override
    public List<T> findAll() throws SQLException {
        return entityManager.createQuery("FROM " + entity.getName()).getResultList();
    }

    @Override
    public T find(ID pk) throws SQLException {
        return entityManager.find(entity,pk);
    }

    @Override
    public void save(T entity) throws SQLException {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) throws SQLException {
        entityManager.merge(entity);
    }

    @Override
    public void delete(ID pk) throws SQLException {
        entityManager.remove(entityManager.getReference(entity,pk));
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
