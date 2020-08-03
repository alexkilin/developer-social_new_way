package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.webapp.converters.PostConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private final PostDtoService postDtoService;
    private final PostConverter postConverter;
    private final PostService postService;

    @Autowired
    public PostController(PostDtoService postDTOService, PostConverter postConverter, PostService postService) {
        this.postDtoService = postDTOService;
        this.postConverter = postConverter;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
       return ResponseEntity.ok(postDtoService.getPosts());
    }

    @GetMapping("/{text}")
    public ResponseEntity<List<PostDto>> getPostsByTag(@PathVariable String text) {
        return ResponseEntity.ok(postDtoService.getPostsByTag(text));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto){
        Post post = postConverter.toEntity(postDto);
        postService.create(post);
        return ResponseEntity.ok().body(post.getId());
    }

}
