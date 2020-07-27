package com.javamentor.developer.social.platform.dao.abstracts.model;

import java.util.List;

public interface ReadWriteDAO<T, PK> {

    void persist(T t);

    void update(T t);

    void delete(T t);

    boolean existsById(PK id);

    List<T> getAll();

    void deleteById(PK id);

    List<T> getUserFriendsById(PK id);
}
