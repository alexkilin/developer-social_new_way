package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private final PostDTOService postDTOService;

    @Autowired
    public PostController(PostDTOService postDTOService) {
        this.postDTOService = postDTOService;
    }

    @GetMapping
    public ResponseEntity<PostDto> getPosts() {
        return ResponseEntity.ok(postDTOService.getPosts());
    }
}
