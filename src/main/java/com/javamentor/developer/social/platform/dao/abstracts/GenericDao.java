package com.javamentor.developer.social.platform.dao.abstracts;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {
    void create(T entity);

    void update(T entity);

    T getById(PK id);

    void delete(T entity);

    void deleteById(PK id);

    boolean existById(PK id);
}
