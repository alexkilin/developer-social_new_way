package com.javamentor.developer.social.platform.webapp.controllers.album;

import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.AlbumImageDTO;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumImageService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.AlbumImageConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/album/image")
@Api(value = "AlbumImagesApi", description = "Операции с фотоальбомами")
public class AlbumImageController {

    private final AlbumImageService   albumImageService;
    private final AlbumService        albumService;
    private final AlbumImageConverter converter;
    private final UserService         userService;


    @Autowired
    public AlbumImageController(AlbumImageService albumImageService, AlbumService albumService,
                                AlbumImageConverter converter, UserService userService) {

        this.albumImageService = albumImageService;
        this.albumService = albumService;
        this.converter = converter;
        this.userService = userService;

    }



/*    @ApiOperation(value = "Удалить фотоальбом по id")
    @GetMapping("/delete")
    public void deleteAlbum(@RequestParam Long id) {

        albumService.deleteById(albumImageService.getById(id).getAlbum().getId());
        albumImageService.deleteById(id);

    }*/

    /*@ApiOperation(value = "Добавить фотоальбом")
    @PostMapping("/create")
    public void createAlbum(@RequestBody AlbumDto DTO, @RequestParam Long userId) {

        Album album = new Album(DTO.getName(), DTO.getIcon(), userService.getById(userId), MediaType.IMAGE,
                LocalDateTime.now(), LocalDateTime.now());
        albumImageService.create(new AlbumImage(album));

    }*/
}
