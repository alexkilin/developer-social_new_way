package com.javamentor.developer.social.platform.service.abstracts.model.media;


import com.javamentor.developer.social.platform.models.entity.genre.Genre;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.Optional;

public interface GenreService extends GenericService<Genre, Long> {

    Optional<Genre> getByTitle(String title);

}
