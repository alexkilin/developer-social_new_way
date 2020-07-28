package com.javamentor.developer.social.platform.service.abstracts;

import java.io.Serializable;

public interface GenericService<T, PK> {
    void create(T entity);

    void update(T entity);

    T getById(PK id);

    void delete(T entity);

    void deleteById(PK id);

    boolean existById(PK id);
}
