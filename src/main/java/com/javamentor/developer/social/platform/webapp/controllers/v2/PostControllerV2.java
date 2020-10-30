package com.javamentor.developer.social.platform.webapp.controllers.v2;

import com.javamentor.developer.social.platform.models.dto.PostCreateDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.entity.comment.CommentType;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.PostCommentService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.MediaService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.UserTabsService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.PostCommentConverter;
import com.javamentor.developer.social.platform.webapp.converters.PostConverter;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v2/posts", produces = "application/json")
@SuppressWarnings("deprecation")
@Api(value = "PostApi-v2", description = "Операции над постами пользователя")
@Validated
public class PostControllerV2 {
    private final PostDtoService postDtoService;
    private final PostConverter postConverter;
    private final PostService postService;
    private final UserService userService;
    private final MediaService mediaService;
    private final UserTabsService userTabsService;
    private final PostCommentConverter postCommentConverter;
    private final PostCommentService postCommentService;

    @Autowired
    public PostControllerV2(PostDtoService postDtoService, PostConverter postConverter, PostService postService, UserService userService, MediaService mediaService, UserTabsService userTabsService, PostCommentConverter postCommentConverter, PostCommentService postCommentService) {
        this.postDtoService = postDtoService;
        this.postConverter = postConverter;
        this.postService = postService;
        this.userService = userService;
        this.mediaService = mediaService;
        this.userTabsService = userTabsService;
        this.postCommentConverter = postCommentConverter;
        this.postCommentService = postCommentService;
    }


    @ApiOperation(value = "Получение списка всех постов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены", responseContainer = "List", response = PostDto.class)})
    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok().body(postDtoService.getAllPosts());
    }

    @ApiOperation(value = "Получение поста по тэгу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены", response = PostDto.class, responseContainer = "List")})
    @GetMapping("/{tag}")
    public ResponseEntity<List<PostDto>> getPostsByTag(@ApiParam(value = "Название тэга", example = "Some tag") @PathVariable("tag") String tag) {
        return ResponseEntity.ok(postDtoService.getPostsByTag(tag));
    }

    @ApiOperation(value = "Получение списка постов пользователя по его ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены", response = PostDto.class, responseContainer = "List")})
    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@ApiParam(value = "ID пользователя", example = "60") @PathVariable Long id) {
        return ResponseEntity.ok(postDtoService.getPostsByUserId(id));
    }

    @ApiOperation(value = "Добавление поста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост добавлен", response = PostDto.class)
    })
    @PostMapping("")
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

    @ApiOperation(value = "Добавление комментария к посту")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Комментарий добавлен"),
            @ApiResponse(code = 404, message = "Пользователь или пост не найдены")
    })
    @PostMapping("/{postId}/comment")
    public ResponseEntity<String> addCommentToPost(@ApiParam(value = "Объект комментария к посту") @RequestBody CommentDto commentDto,
                                                   @ApiParam(value = "Идентификатор поста", example = "1") @PathVariable @NonNull Long postId) {
        if (!userService.existById(commentDto.getUserDto().getUserId())) {
            String msg = String.format("Пользователь с id: %d не найден", commentDto.getUserDto().getUserId());
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        if (!postService.existById(postId)) {
            String msg = String.format("Пост с id: %d не найден", postId);
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        PostComment postComment = postCommentConverter.toPostCommentEntity(commentDto, postId);
        postComment.getComment().setCommentType(CommentType.POST);
        postCommentService.create(postComment);
        String msg = String.format("Пользователь с id: %d добавил комментарий в пост с id: %s", commentDto.getUserDto().getUserId(), postId);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }
}
