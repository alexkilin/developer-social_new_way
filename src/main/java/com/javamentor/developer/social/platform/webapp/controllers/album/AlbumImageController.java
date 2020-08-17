package com.javamentor.developer.social.platform.webapp.controllers.album;

import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/album/image/")
public class AlbumImageController {
    private final AlbumImageService service;

    @Autowired
    public AlbumImageController(AlbumImageService service) {
        this.service = service;
    }

    @GetMapping("all")
    public ResponseEntity<List<AlbumImage>> all(@RequestParam Long id) {
        return new ResponseEntity<>(service.getAllByUserId(id), HttpStatus.OK);
    }

}
