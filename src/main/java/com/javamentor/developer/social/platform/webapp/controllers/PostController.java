package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.MediaService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.UserTabsService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.PostConverter;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/posts", produces = "application/json")
@Api(value = "PostApi", description = "Операции с постами пользователя (получение, добавление, удаление)")
public class PostController {
    private final PostDtoService postDtoService;
    private final PostConverter postConverter;
    private final PostService postService;
    private final UserService userService;
    private final MediaService mediaService;
    private final UserTabsService userTabsService;

    @Autowired
    public PostController(PostDtoService postDtoService, PostConverter postConverter, PostService postService, UserService userService, MediaService mediaService, UserTabsService userTabsService) {
        this.postDtoService = postDtoService;
        this.postConverter = postConverter;
        this.postService = postService;
        this.userService = userService;
        this.mediaService = mediaService;
        this.userTabsService = userTabsService;
    }


    @ApiOperation(value = "Получение списка всех постов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены", responseContainer = "List", response = PostDto.class)})
    @GetMapping
        public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok().body(postDtoService.getPosts());
    }

    @ApiOperation(value = "Получение поста по тэгу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены",response = PostDto.class, responseContainer = "List")})
    @GetMapping("/{text}")
    public ResponseEntity<List<PostDto>> getPostsByTag(@ApiParam(value = "Название тэга", example = "Some tag")@PathVariable String text) {
        return ResponseEntity.ok(postDtoService.getPostsByTag(text));
    }

    @ApiOperation(value = "Получение списка постов пользователя по его ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены",response = PostDto.class, responseContainer = "List")})
    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@ApiParam(value = "ID пользователя", example = "20")@PathVariable Long id) {
        return ResponseEntity.ok(postDtoService.getPostsByUserId(id));
    }

    @ApiOperation(value = "Добавление поста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост добавлен", response = String.class)
    })
    @PostMapping("/add")
    public ResponseEntity<?> addPost(@ApiParam(value = "Объект добавляемого поста") @RequestBody PostDto postDto) {
        List<MediaPostDto> mediaPostDtoList = postDto.getMedia();
        Set<Media> mediaSet = new HashSet<>();

        Post post = postConverter.toEntity(postDto);

        for (MediaPostDto mediaPostDto :
                mediaPostDtoList) {
            User user = userService.getById(mediaPostDto.getUserId());
            Media media = postConverter.toEntityMedia(mediaPostDto);
            media.setUser(user);
            mediaSet.add(media);
            mediaService.create(media);
        }

        User user = userService.getById(postDto.getUserId());

        post.setMedia(mediaSet);
        post.setUser(user);
        postService.create(post);
        return ResponseEntity.ok().body(post.getText());
    }

    @ApiOperation(value = "Удаление поста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост удалён", response = String.class),
            @ApiResponse(code = 400, message = "Пост не может быть удалён", response = String.class)
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePost(@ApiParam(value = "ID пользователя", example = "20") @PathVariable @NotNull Long id) {
        if (postService.existById(id)) {

            Post post = postService.getById(id);

            userTabsService.deletePost(post);

            return ResponseEntity.ok().body(String.format("Deleted Post with ID %d, is successful", id));
        } else {
            return ResponseEntity.badRequest().body(String.format("Can't find Post with ID %d", id));
        }
    }

    @ApiOperation(value = "Получение всех коментов поста по id поста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Комментарии получены",  responseContainer = "List",
                    response = PostDto.class)})
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> showPostComments(@ApiParam(value = "ID пользователя", example = "20") @PathVariable Long id) {
        return new ResponseEntity<>(postDtoService.getCommentsByPostId(id), HttpStatus.OK);
    }
}
