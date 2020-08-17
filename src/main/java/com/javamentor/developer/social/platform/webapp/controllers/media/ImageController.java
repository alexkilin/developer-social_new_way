package com.javamentor.developer.social.platform.webapp.controllers.media;

import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.service.abstracts.model.media.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/api/image")
@Api(value = "ImageApi")
public class ImageController {

    private final ImageService service;


    @Autowired
    public ImageController(ImageService service) {
        this.service = service;
    }

    @ApiOperation(value = "Все фото пользователя по id")
    @RequestMapping("all")
    public ResponseEntity<List<Image>> all(@RequestParam Long id) {
        return new ResponseEntity<>(service.getAllByUserId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Удалить по id")
    @RequestMapping("del")
    public void del(@RequestParam Long id) {
        service.deleteById(id);
    }

    @ApiOperation(value = "Добавить")
    @RequestMapping("create")
    public void create(@RequestParam Image image) {
        service.create(image);
    }
}
