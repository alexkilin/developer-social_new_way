package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private final PostDtoService postDtoService;

    @Autowired
    public PostController(PostDtoService postDTOService) {
        this.postDtoService = postDTOService;
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
       return ResponseEntity.ok(postDtoService.getPosts());
    }
}
