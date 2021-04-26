package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.GenreDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.Map;
import java.util.Optional;

public interface GenreDtoService {
    Optional<GenreDto> getGenreByTitle(String title);
    PageDto<GenreDto, Object> getAllGenres(Map<String, Object> parameters );
}
