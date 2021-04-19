package com.javamentor.developer.social.platform.webapp.controllers.v2.user;

import com.javamentor.developer.social.platform.models.dto.media.music.GenreDto;
import com.javamentor.developer.social.platform.models.entity.genre.Genre;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.service.abstracts.dto.GenreDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.GenreService;
import com.javamentor.developer.social.platform.webapp.converters.GenreConverter;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v2/genres", produces = "application/json")
@SuppressWarnings("deprecation")
@Api(value = "GenresApi-v2", description = "Операции над жанрами")
public class GenreControllerV2 {

    private final GenreDtoService genreDtoService;
    private final GenreService genreService;
    private final GenreConverter genreConverter;
    @Autowired
    public GenreControllerV2(GenreDtoService genreDtoService,GenreService genreService,GenreConverter genreConverter) {
        this.genreDtoService = genreDtoService;
        this.genreService = genreService;
        this.genreConverter = genreConverter;
    }

    @ApiOperation(value = "Добавление нового жанра")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Жанр добавлен", response = GenreDto.class) ,
            @ApiResponse(code = 400, message = "Жанр уже существует", response = String.class)
    })
    @Validated(OnCreate.class)
    @PostMapping(value = "/add")
    public ResponseEntity<?> addGroupCategory(@ApiParam(value = "Жанр") @Valid @RequestBody GenreDto genreDto ) {
        if(genreDtoService.getGenreByTitle(genreDto.getTitle()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(String.format("Genre with title \"%s\" already exist"
                            , genreDto.getTitle()));
        }
        Genre genre = genreConverter.toEntity(genreDto);
        genreService.create(genre);

        return ResponseEntity.ok(genreConverter.toDto(genre));
    }

    @ApiOperation(value = "Удаление жанра")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Жанр удален", response = String.class),
            @ApiResponse(code = 400, message = "Invalid genre id", response = String.class)})
    @DeleteMapping(value = "/{genre_id}")
    public ResponseEntity<?> deleteGenre(@ApiParam(value = "Id жанра")
                                         @NotNull @PathVariable Long genre_id) {

        Optional<Genre> genreOptional = genreService.getById(genre_id);
        if (!genreOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Genre with id %d is not found", genre_id));
        }
        genreOptional.get().setTitle("Не определено");
        genreService.update(genreOptional.get());
        return ResponseEntity.ok("Deleted");
    }



}
