package com.javamentor.developer.social.platform.webapp.controllers.media;

import com.javamentor.developer.social.platform.models.dto.ImageDTO;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.ImageService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.ImageConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/image/")
@Api(value = "ImageApi")
public class ImageController {

    private final ImageService   service;
    private final ImageConverter converter;
    private final UserService    userService;
    private final AlbumService   albumService;

    @Autowired
    public ImageController(ImageService service, @Qualifier("imageConverterImpl") ImageConverter converter,
                           UserService userService, AlbumService albumService) {
        this.service = service;
        this.converter = converter;
        this.userService = userService;
        this.albumService = albumService;
    }

    @ApiOperation(value = "Все фото пользователя по id")
    @GetMapping("all")
    public ResponseEntity<List<ImageDTO>> all(@RequestParam Long id) {
        List<Image> lstImg = service.getAllByUserId(id);
        List<ImageDTO> lstDTO = new ArrayList<>();
        lstImg.forEach(img -> lstDTO.add(converter.getDTO(img)));
        return new ResponseEntity<>(lstDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Удалить по id")
    @GetMapping("del")
    public void del(@RequestParam Long id) {
        service.deleteById(id);
    }

    @ApiOperation(value = "Добавить")
    @PostMapping("create")
    public void create(@RequestParam Long userId, @RequestParam String url,
                       @RequestParam Long albumId, @RequestParam String description) {
        Media media = new Media(userService.getById(userId), url, MediaType.IMAGE, LocalDateTime.now(), albumService.getById(albumId));
        service.create(new Image(media, description));

    }
}