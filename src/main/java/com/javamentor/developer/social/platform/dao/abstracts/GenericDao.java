package com.javamentor.developer.social.platform.dao.abstracts;

import com.javamentor.developer.social.platform.models.dto.AudioDto;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {
    void create(T entity);

    void update(T entity);

    T getById(PK id);

    void delete(T entity);

    void deleteById(PK id);

    boolean existById(PK id);

    List<T> getAll();

    List<T> getPartAudio(int currentPage, int itemsOnPage);
}
