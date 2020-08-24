package com.javamentor.developer.social.platform.webapp.controllers.album;

import com.javamentor.developer.social.platform.models.dto.AlbumImageDTO;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumImageService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.AlbumImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/album/image/")
public class AlbumImageController {
    private final AlbumImageService   service;
    private final AlbumImageConverter converter;
    private final UserService         userService;


    @Autowired
    public AlbumImageController(AlbumImageService service, AlbumImageConverter converter, UserService userService) {
        this.service = service;
        this.converter = converter;
        this.userService = userService;
    }

    @GetMapping("all")
    public ResponseEntity<List<AlbumImageDTO>> all(@RequestParam Long id) {
        List<AlbumImage> lst = service.getAllByUserId(id);
        List<AlbumImageDTO> lstDTO = new ArrayList<>();
        lst.forEach(alb -> lstDTO.add(converter.getDTO(alb)));
        return new ResponseEntity<>(lstDTO, HttpStatus.OK);
    }

    @GetMapping("del")
    public void del(@RequestParam Long id) {
        service.deleteById(id);
    }

    @PostMapping("create")
    public void create(@RequestParam String name, @RequestParam String iconUrl, @RequestParam Long userId) {

        Album album = new Album(name, iconUrl, userService.getById(userId), MediaType.IMAGE,
                LocalDateTime.now(), LocalDateTime.now());
        service.create(new AlbumImage(album));

    }
}
