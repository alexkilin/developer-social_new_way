package com.javamentor.developer.social.platform.webapp.controllers.v1;

import com.javamentor.developer.social.platform.models.dto.PostCreateDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.util.OnCreate;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping(value = "api/posts", produces = "application/json")
@Api(value = "PostApi", description = "Операции с постами пользователя (получение, добавление, удаление)")
@Validated
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

    @ApiOperation(value = "Добавление поста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост добавлен", response = PostDto.class)
    })
    @PostMapping("/add")
    @Validated(OnCreate.class)
    public ResponseEntity<PostDto> addPost(@ApiParam(value = "Объект добавляемого поста") @RequestBody @Valid @NotNull PostCreateDto postCreateDto) {
        Post post = postConverter.toEntity(postCreateDto);
        postService.create(post);
        return ResponseEntity.ok().body(postConverter.toDto(post));
    }

    @ApiOperation(value = "Удаление поста по id поста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост удалён", response = String.class),
            @ApiResponse(code = 400, message = "Пост не может быть удалён", response = String.class)
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePost(@ApiParam(value = "ID поста", example = "20") @PathVariable @NotNull Long id) {
        if (postService.existById(id)) {

            Post post = postService.getById(id).get();

            userTabsService.deletePost(post);

            return ResponseEntity.ok().body(String.format("Deleted Post with ID %d, is successful", id));
        } else {
            return ResponseEntity.badRequest().body(String.format("Can't find Post with ID %d", id));
        }
    }

    @ApiOperation(value = "Получение всех комментариев поста по id поста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Комментарии получены", responseContainer = "List",
                    response = CommentDto.class)})
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> showPostComments(@ApiParam(value = "ID поста", example = "20") @PathVariable Long id) {
        return new ResponseEntity<>(postDtoService.getCommentsByPostId(id), HttpStatus.OK);
    }
}
