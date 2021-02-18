package com.javamentor.developer.social.platform.webapp.controllers.v2.user;

import com.javamentor.developer.social.platform.models.dto.media.AlbumCreateDto;
import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageCreateDto;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumImageDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.ImageDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumImageService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.ImageService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.MediaService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.AlbumImageConverter;
import com.javamentor.developer.social.platform.webapp.converters.ImageConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@Validated
@RequestMapping(value = "/api/v2/images")
@SuppressWarnings("deprecation")
@Api(value = "ImageApi-v2", description = "Операции над изображениями")
public class ImageControllerV2 {

    private final ImageDtoService imageDTOService;
    private final ImageService imageService;
    private final AlbumImageDtoService albumImageDtoService;
    private final AlbumImageService albumImageService;
    private final UserService userService;
    private final AlbumImageConverter albumImageConverter;
    private final AlbumService albumService;
    private final MediaService mediaService;
    private final ImageConverter imageConverter;

    @Autowired
    public ImageControllerV2( ImageDtoService imageDTOService , ImageService imageService , AlbumImageDtoService albumImageDtoService ,
                              AlbumImageService albumImageService , UserService userService , AlbumImageConverter albumImageConverter ,
                              AlbumService albumService , MediaService mediaService , ImageConverter imageConverter ) {
        this.imageDTOService = imageDTOService;
        this.imageService = imageService;
        this.albumImageDtoService = albumImageDtoService;
        this.albumImageService = albumImageService;
        this.userService = userService;
        this.albumImageConverter = albumImageConverter;
        this.albumService = albumService;
        this.mediaService = mediaService;
        this.imageConverter = imageConverter;
    }

    @ApiOperation(value = "Создать изображение")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Изображение создано", response = ImageDto.class)})
    @PostMapping
    @Validated(OnCreate.class)
    public ResponseEntity<?> createImage( @ApiParam(value = "объект изображения")
                                          @RequestBody @Valid ImageCreateDto imageCreateDto ) {
        Image newImage = imageConverter.toEntity(imageCreateDto);
        imageService.create(newImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageConverter.toImageDto(newImage));
    }

    @ApiOperation(value = "Удалить изображение")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Изображение удалено", response = String.class) ,
            @ApiResponse(code = 400, message = "Invalid image id", response = String.class)})
    @DeleteMapping(value = "/{imageId}")
    public ResponseEntity<?> deleteImage(@ApiParam(value = "Id изображения") @NotNull @PathVariable Long imageId ) {
        imageService.deleteById(imageId);
        return ResponseEntity.ok("Deleted");
    }

    @ApiOperation(value = "Получить изображение по Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Изображение получено", response = ImageDto.class) ,
            @ApiResponse(code = 404, message = "Изображение не найдено", response = String.class)})
    @GetMapping(value = "/{imageId}")
    public ResponseEntity<?> getImageById( @ApiParam(value = "Id изображения")
                                           @NotNull @PathVariable Long imageId ) {
        Optional<ImageDto> optional = imageDTOService.getById(imageId);
        if(!optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Image with id %s not found" , imageId));
        }
        return ResponseEntity.ok().body(optional.get());
    }

    @ApiOperation(value = "Получить все изображения по Id пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Изображения получены", response = ImageDto.class, responseContainer = "List") ,
            @ApiResponse(code = 404, message = "Пользователь не найден", response = String.class)})
    @GetMapping(params = {"currentPage" , "itemsOnPage" , "userId"})
    public ResponseEntity<?> getAllImagesOfUser( @ApiParam(value = "Id пользователя", example = "60") @RequestParam("userId") @NotNull Long userId ,
                                                 @ApiParam(value = "Текущая страница", example = "0")
                                                 @RequestParam("currentPage") int currentPage ,
                                                 @ApiParam(value = "Количество данных на страницу", example = "15")
                                                 @RequestParam("itemsOnPage") int itemsOnPage ) {
        if(!userService.existById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No user with id %s found" , userId));
        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId" , userId);
        parameters.put("currentPage" , currentPage);
        parameters.put("itemsOnPage" , itemsOnPage);
        return ResponseEntity.status(HttpStatus.OK).body(imageDTOService.getAllByUserId(parameters));
    }

    @ApiOperation(value = "Создать фотоальбом")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Альбом создан", response = AlbumDto.class) ,
            @ApiResponse(code = 400, message = "Фотоальбом с таким именем уже существует", response = String.class) ,
            @ApiResponse(code = 404, message = "Пользователь не найден", response = String.class)})
    @Validated(OnCreate.class)
    @PostMapping(value = "/user/{userId}/album")
    public ResponseEntity<?> createAlbum( @ApiParam(value = "объект альбома")
                                          @Valid @NotNull @RequestBody AlbumCreateDto albumCreateDto , @ApiParam(value = "id пользователя") @PathVariable Long userId ) {
        if(albumService.existsByNameAndMediaType(albumCreateDto.getName() , MediaType.IMAGE)) {
            return ResponseEntity.badRequest()
                    .body(String.format("Image album with name '%s' already exists" , albumCreateDto.getName()));
        }
        AlbumImage albumImage = albumImageService.createAlbumImageWithOwner(albumImageConverter.toAlbumImage(albumCreateDto));
        return ResponseEntity.ok().body(albumImageConverter.toAlbumImageDto(albumImage));
    }

    @ApiOperation(value = "Удалить фотоальбом")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Фотоальбом удален", response = String.class) ,
            @ApiResponse(code = 400, message = "Invalid album id", response = String.class)})
    @DeleteMapping(value = "/albums/{albumId}")
    public ResponseEntity<?> deleteAlbum( @ApiParam(value = "Id альбома")
                                          @NotNull @PathVariable Long albumId ) {
        albumImageService.deleteById(albumId);
        return ResponseEntity.ok("Deleted");
    }

    @ApiOperation(value = "Добавить существующее изображение в фотоальбом")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Изображение добавлено", response = String.class) ,
            @ApiResponse(code = 404, message = "Фотоальбом не найден", response = String.class) ,
            @ApiResponse(code = 404, message = "Изображение не найдено", response = String.class),
            @ApiResponse(code = 400, message = "Изображение уже есть в альбоме", response = String.class)
    })
    @PutMapping(value = "/albums/{albumId}/images")
    public ResponseEntity<?> addImageToAlbum(
            @ApiParam(value = "Id альбома", example = "11")
            @PathVariable @NotNull Long albumId ,
            @ApiParam(value = "Id изображения", example = "imageId=31")
            @RequestParam @NotNull Long imageId ) {
        Optional<AlbumImage> album = albumImageService.getByIdWithImages(albumId);
        if(!album.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Image album with id %s is not found" , albumId));
        }
        Optional<Image> image = imageService.getById(imageId);
        if(!image.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Image with id %s is not found" , imageId));
        }
        if(!album.get().getImages().add(image.get())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Image with id %s is already in album with id %s", imageId,albumId));
        }
        albumImageService.update(album.get());
        return ResponseEntity.ok().body(String.format("Image id %s added to album id %s" , imageId , albumId));
    }

    @ApiOperation(value = "Удалить существующее изображение из фотоальбома")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Изображение удалено", response = String.class) ,
            @ApiResponse(code = 404, message = "Фотоальбом не найден", response = String.class) ,
            @ApiResponse(code = 404, message = "Изображение не найдено", response = String.class)})
    @DeleteMapping(value = "/albums/{albumId}/images")
    public ResponseEntity<?> removeImageFromAlbum( @ApiParam(value = "Id альбома", example = "11") @PathVariable @NotNull Long albumId ,
                                                   @ApiParam(value = "Id изображения", example = "31") @RequestParam(value = "id") @NotNull Long imageId ) {
        Optional<AlbumImage> optionalAlbumImage = albumImageService.getById(albumId);
        Optional<Image> optionalImage = imageService.getById(imageId);
        Optional<Media> optionalMedia = mediaService.getById(imageId);
        if(!optionalAlbumImage.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Album with id %s not found" , albumId));
        }
        if(!optionalImage.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Image with id %s not found" , imageId));
        }
        if(optionalMedia.isPresent()) {
            Media media = optionalMedia.get();
            if(Objects.nonNull(media.getAlbum())) {
                media.setAlbum(null);
                mediaService.update(media);
            }
        }
        return ResponseEntity.ok().body(String.format("Image %s removed from album %s" , imageId , albumId));
    }

    @ApiOperation(value = "Получить содержимое фотоальбома")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = ImageDto.class, responseContainer = "List") ,
            @ApiResponse(code = 404, message = "", response = String.class) ,
            @ApiResponse(code = 404, message = "", response = String.class)
    })
    @GetMapping(value = "/albums/{albumId}/images", params = {"currentPage" , "itemsOnPage"})
    public ResponseEntity<?> getImagesFromAlbumById(
            @ApiParam(value = "Id фотоальбома", example = "101") @PathVariable @NotNull Long albumId ,
            @ApiParam(value = "Текущая страница", example = "0") @RequestParam("currentPage") int currentPage ,
            @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage ) {
        if(!albumImageService.existById(albumId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Album with id %s not found" , albumId));
        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("albumId" , albumId);
        parameters.put("currentPage" , currentPage);
        parameters.put("itemsOnPage" , itemsOnPage);
        return ResponseEntity.ok(imageDTOService.getAllByAlbumId(parameters));
    }

    @ApiOperation(value = "Получить фотоальбом по Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Фотоальбом получен", response = AlbumDto.class, responseContainer = "List") ,
            @ApiResponse(code = 404, message = "Фотоальбом не найден", response = String.class)})
    @GetMapping(value = "/albums/{albumId}")
    public ResponseEntity<?> getImageAlbumById(@ApiParam(value = "Id альбома", example = "11")
                                               @PathVariable @NotNull Long albumId) {
        Optional<AlbumImage> optionalAlbum = albumImageService.getById(albumId);
        if(!optionalAlbum.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Album with id %s not found", albumId));
        }
        return ResponseEntity.ok(albumImageConverter.toAlbumImageDto(optionalAlbum.get()));
    }

    @ApiOperation(value = "Получить все фотоальбомы пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Фотоальбомы получены", response = AlbumDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Пользователь не найден", response = String.class)})
    @GetMapping(value = "/albums", params = {"currentPage", "itemsOnPage", "userId"})
    public ResponseEntity<?> getAllImageAlbumsOfUser(
            @ApiParam(value = "Id пользователя", example = "60") @RequestParam("userId") @NotNull Long userId,
            @ApiParam(value = "Текущая страница", example = "0") @RequestParam("currentPage") int currentPage,
            @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        if(!userService.existById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No user with id %s found", userId));
        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.status(HttpStatus.OK).body(albumImageDtoService.getAllByUserId(parameters));
    }
}