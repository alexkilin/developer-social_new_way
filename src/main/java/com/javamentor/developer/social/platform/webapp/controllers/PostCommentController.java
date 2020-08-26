package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.entity.comment.CommentType;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.PostCommentService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.PostCommentConverter;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/postsComments")
@Api(value = "PostsCommentsApi", description = "Операции над комментариями в постах")
public class PostCommentController {
    public final PostService postService;
    public final PostCommentConverter postCommentConverter;
    public final PostCommentService postCommentService;
    public final UserService userService;

    @Autowired
    public PostCommentController(PostService postService, PostCommentConverter postCommentConverter, PostCommentService postCommentService, UserService userService) {
        this.postService = postService;
        this.postCommentConverter = postCommentConverter;
        this.postCommentService = postCommentService;
        this.userService = userService;
    }

    @ApiOperation(value = "Добавление комментария к посту")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Комментарий добавлен"),
            @ApiResponse(code = 404, message = "Пользователь или пост не найдены")
    })
    @PostMapping("/{postId}/comment")
    public ResponseEntity<String> addCommentToPost(@RequestBody CommentDto commentDto,
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
