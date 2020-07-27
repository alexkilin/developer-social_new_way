package com.javamentor.developer.social.platform.service.abstracts.model;

import java.util.List;

public interface ReadWriteService<T, PK>  {

    void persist(T t);

    void update(T t);

    void delete (T t);

    void deleteById(PK id);

    boolean existsById(PK id);

    List<T> getAll();

    List<T> getUserFriendsById(PK id);
}
