package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.MediaService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.UserTabsService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.PostConverter;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/posts")
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
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены"),
            @ApiResponse(code = 400, message = "Посты не могут быть получены")
    })

    public ResponseEntity<List<PostDto>> getPosts() {
      return ResponseEntity.ok(postDtoService.getPosts());
     //   return ResponseEntity.ok().body(postService..stream().map(audioConverter::toDTO).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Получение поста по тэгу")
    @GetMapping("/{text}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены"),
            @ApiResponse(code = 400, message = "Посты не могут быть получены")
    })
    public ResponseEntity<List<PostDto>> getPostsByTag(@PathVariable String text) {
        return ResponseEntity.ok(postDtoService.getPostsByTag(text));
    }

    @ApiOperation(value = "Добавление поста")
    @PostMapping("/add")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты добавлен"),
            @ApiResponse(code = 400, message = "Посты не может быть добавлен")
    })
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto){
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

    @ApiOperation(value = "Удаление поста (параметр ID обязателен)")
    @DeleteMapping(path = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост удалён"),
            @ApiResponse(code = 400, message = "Пост не может быть удалён")
    })
    public ResponseEntity<?> deletePost(@PathVariable @NotNull Long id) {
        if (postService.existById(id)) {

            Post post = postService.getById(id);

            userTabsService.deletePost(post);

            return ResponseEntity.ok().body(String.format("Deleted Post with ID %d, is successful", id));
        }
        else {
            return ResponseEntity.badRequest().body(String.format("Can't find Post with ID %d", id));
        }
    }

}
