package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.GenreDto;
import java.util.Optional;

public interface GenreDtoDao {

    Optional<GenreDto> getGenreByTitle(String title);
}
