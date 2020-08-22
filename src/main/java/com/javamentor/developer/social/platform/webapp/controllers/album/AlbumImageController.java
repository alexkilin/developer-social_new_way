package com.javamentor.developer.social.platform.webapp.controllers.album;

import com.javamentor.developer.social.platform.models.dto.AlbumImageDTO;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumImageService;
import com.javamentor.developer.social.platform.webapp.converters.AlbumImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/album/image/")
public class AlbumImageController {
    private final AlbumImageService   service;
    private final AlbumImageConverter converter;


    @Autowired
    public AlbumImageController(AlbumImageService service, AlbumImageConverter converter) {
        this.service = service;
        this.converter = converter;
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

    @GetMapping("create")
    public void create(@RequestParam AlbumImageDTO DTO) {

    }
}
