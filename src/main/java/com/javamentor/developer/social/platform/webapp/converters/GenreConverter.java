package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.media.music.GenreDto;
import com.javamentor.developer.social.platform.models.entity.genre.Genre;
import com.javamentor.developer.social.platform.service.abstracts.model.media.GenreService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public abstract class GenreConverter {
    private GenreService genreService;

    @Autowired
    protected void setGenreService (GenreService genreService){
        this.genreService = genreService;
    }

    @Mapping(source = "title", target = "title")
    public abstract Genre toEntity(GenreDto genreDto);

    @Mapping(source = "title", target = "title")
    public abstract GenreDto toDto(Genre genre);
}
