package com.javamentor.developer.social.platform.webapp.controllers.media;

import com.javamentor.developer.social.platform.models.dto.ImageDTO;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.service.abstracts.dto.ImageDTOService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.ImageService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.MediaService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.ImageConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/image")
@Api(value = "ImageApi")
public class ImageController {

    private final ImageService    imageService;
   // private final MediaService    mediaService;
    private final UserService     userService;
    private final AlbumService    albumService;

    private final ImageDTOService imageDTOService;

    @Autowired
    public ImageController(ImageService imageService, /*MediaService mediaService,*/ UserService userService,
                           AlbumService albumService, ImageDTOService imageDTOService) {

        this.imageService = imageService;
        //this.mediaService = mediaService;
        this.userService = userService;
        this.albumService = albumService;
        this.imageDTOService = imageDTOService;


    }

    @ApiOperation(value = "Все фото пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Фото получены", response = ImageDTO.class)})
    @GetMapping("/all")
    public ResponseEntity<List<ImageDTO>> allAlbum(@RequestParam Long id) {

        List<ImageDTO> lst = imageDTOService.getAllByUserId(id);

        return new ResponseEntity<>(lst, HttpStatus.OK);

    }

/*    @ApiOperation(value = "Удалить фото по id")
    @GetMapping("/delete")
    public void deleteImage(@RequestParam Long id) {

        Image image = imageService.getById(id);
        Long mId = image.getMedia().getId();
        imageService.deleteById(id);
        mediaService.deleteById(mId);

    }*/

    /*@ApiOperation(value = "Добавить фото")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Фото добавлено")})
    @PostMapping("/create")
    public void createImage(@RequestParam Long userId, @RequestParam String url,
                            @RequestParam Long albumId, @RequestParam String description) {

        Media media = new Media(userService.getById(userId), url, MediaType.IMAGE,
                LocalDateTime.now(), albumService.getById(albumId));
        imageService.create(new Image(media, description));

    }*/


}