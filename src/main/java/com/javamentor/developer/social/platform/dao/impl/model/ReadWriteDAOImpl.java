package com.javamentor.developer.social.platform.dao.impl.model;

import com.javamentor.developer.social.platform.dao.abstracts.model.ReadWriteDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;


@Repository
public abstract class ReadWriteDAOImpl<T, PK> implements ReadWriteDAO<T, PK> {

    protected Class<T> tClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public ReadWriteDAOImpl() {
        this.tClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    @Transactional
    public void persist(T t) {
        entityManager.persist(t);
    }

    @Override
    @Transactional
    public void update(T t) {
        entityManager.merge(t);
    }

    @Override
    @Transactional
    public void delete(T t) {
        entityManager.remove(entityManager.contains(t) ? t : entityManager.merge(t));
    }

    @Override
    public boolean existsById(PK id) {
        return entityManager.find(tClass, id) != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAll() {
        return entityManager.createQuery("from " + tClass.getName()).getResultList();
    }

    @Override
    public void deleteById(PK id) {
        entityManager.createQuery("delete from User u where u.userId = " + id);
    }

    @Override
    public List<T> getUserFriendsById(PK id) {
        return entityManager.createQuery("select * " +
                                            "from friends f " +
                                            "join users u " +
                                            "on f.user_id = u.id" +
                                            "where u.id = " + id)
                                            .getResultList();
    }
}
