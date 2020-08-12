package com.javamentor.developer.social.platform.service.abstracts;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T, PK> {

    List<T> getAll();

    List<T> getPart(int currentPage, int itemsOnPage);

    void create(T entity);

    void update(T entity);

    T getById(PK id);

    void delete(T entity);

    void deleteById(PK id);

    boolean existById(PK id);


}
