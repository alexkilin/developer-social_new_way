package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.entity.comment.CommentType;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.PostCommentService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.PostCommentConverter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/postsComments")
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
    @PostMapping("/{postId}/comment")
    public ResponseEntity<Void> addCommentToPost(@RequestBody CommentDto commentDto, @PathVariable Long postId) {
        if (!userService.existById(commentDto.getUserDto().getUserId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!postService.existById(postId)) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PostComment postComment = postCommentConverter.toPostCommentEntity(commentDto, postId);
        postComment.getComment().setCommentType(CommentType.POST);
        postCommentService.create(postComment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
