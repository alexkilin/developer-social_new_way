package com.javamentor.developer.social.platform.service.abstracts.model.group;

import com.javamentor.developer.social.platform.models.entity.genre.Genre;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.Optional;

public interface GenreService extends GenericService<Genre, Long> {

    Optional<Genre> getGenreByTitle(String category );
}
