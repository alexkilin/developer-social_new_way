package com.javamentor.developer.social.platform.webapp.controllers.media;

import com.javamentor.developer.social.platform.models.dto.ImageDTO;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.service.abstracts.model.media.ImageService;
import com.javamentor.developer.social.platform.webapp.converters.ImageConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/image/")
@Api(value = "ImageApi")
public class ImageController {

    private final ImageService   service;
    private final ImageConverter converter;

    @Autowired
    public ImageController(ImageService service, @Qualifier("imageConverterImpl") ImageConverter converter) {
        this.service = service;
        this.converter = converter;
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
    @GetMapping("create")
    public void create(@RequestParam ImageDTO DTO, @RequestParam Long userId,
                       @RequestParam String url, @RequestParam Long albumId) {

        service.create(converter.getImage(DTO, userId, url, albumId));

    }
}
