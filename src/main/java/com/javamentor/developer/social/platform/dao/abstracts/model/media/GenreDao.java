package com.javamentor.developer.social.platform.dao.abstracts.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.genre.Genre;

import java.util.Optional;

public interface GenreDao extends GenericDao<Genre, Long> {
    Optional<Genre> getByTitle(String title);
}
