package com.javamentor.developer.social.platform.webapp.controllers.v2;

import com.javamentor.developer.social.platform.models.dto.PostCreateDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.entity.comment.CommentType;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import com.javamentor.developer.social.platform.models.entity.post.Bookmark;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.post.Repost;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.PostCommentService;
import com.javamentor.developer.social.platform.service.abstracts.model.like.PostLikeService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.MediaService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.BookmarkService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.RepostService;
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
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/v2", produces = "application/json")
@Api(value = "PostApi-v2", description = "Операции над постами пользователя")
@Validated
public class PostControllerV2 {
    private final PostDtoService postDtoService;
    private final PostConverter postConverter;
    private final PostService postService;
    private final UserService userService;
    private final UserTabsService userTabsService;
    private final PostCommentConverter postCommentConverter;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;
    private final BookmarkService bookmarkService;
    private final RepostService repostService;

    @Autowired
    public PostControllerV2(PostDtoService postDtoService,
                            PostConverter postConverter,
                            PostService postService,
                            UserService userService,
                            MediaService mediaService,
                            UserTabsService userTabsService,
                            PostCommentConverter postCommentConverter,
                            PostCommentService postCommentService,
                            PostLikeService postLikeService,
                            BookmarkService bookmarkService,
                            RepostService repostService) {

        this.postDtoService = postDtoService;
        this.postConverter = postConverter;
        this.postService = postService;
        this.userService = userService;
        this.userTabsService = userTabsService;
        this.postCommentConverter = postCommentConverter;
        this.postCommentService = postCommentService;
        this.postLikeService = postLikeService;
        this.bookmarkService = bookmarkService;
        this.repostService = repostService;
    }


    @ApiOperation(value = "Получение списка всех постов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены", responseContainer = "List", response = PostDto.class)})
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok().body(postDtoService.getAllPosts());
    }

    @ApiOperation(value = "Получение поста по тэгу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены", response = PostDto.class, responseContainer = "List")})
    @GetMapping("/posts/{tag}")
    public ResponseEntity<List<PostDto>> getPostsByTag(@ApiParam(value = "Название тэга", example = "Some tag") @PathVariable("tag") String tag) {
        return ResponseEntity.ok(postDtoService.getPostsByTag(tag));
    }

    @ApiOperation(value = "Получение всех существующих тегов")
    @ApiResponses(value =  {
            @ApiResponse(code = 200, message = "Теги получены", responseContainer = "List", response = TagDto.class)})
    @GetMapping("/posts/tags")
    public ResponseEntity<List<TagDto>> getAllTags() {
        return ResponseEntity.ok(postDtoService.getAllTags());
    }


    @ApiOperation(value = "Получение списка постов пользователя по его ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены", response = PostDto.class, responseContainer = "List")})
    @GetMapping("/posts/user/{id}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@ApiParam(value = "ID пользователя", example = "60") @PathVariable Long id) {
        return ResponseEntity.ok(postDtoService.getPostsByUserId(id));
    }

    @ApiOperation(value = "Добавление поста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост добавлен", response = PostDto.class)
    })
    @PostMapping("/post")
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
    @DeleteMapping(path = "/post/{id}")
    public ResponseEntity<?> deletePost(@ApiParam(value = "ID поста", example = "20") @PathVariable @NotNull Long id) {
        Optional<Post> result = postService.getById(id);

        if (result.isPresent()) {

            Post post = result.get();

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
    @GetMapping("/post/{id}/comments")
    public ResponseEntity<List<CommentDto>> showPostComments(@ApiParam(value = "ID поста", example = "20") @PathVariable Long id) {
        return new ResponseEntity<>(postDtoService.getCommentsByPostId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Добавление комментария к посту")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Комментарий добавлен"),
            @ApiResponse(code = 404, message = "Пользователь или пост не найдены")
    })
    @PostMapping("/post/{postId}/comment")
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

    @ApiOperation(value = "Добавление лайка посту авторизованным пользователем")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Лайк добавлен в пост")
    })
    @PostMapping("/post/{postId}/like")
    public ResponseEntity<String> addLikeToPost(
            @ApiParam(value = "Id поста", example = "1")
            @PathVariable @NonNull Long postId) {

        Optional<Post> optionalPost = postService.getById(postId);
        User user = userService.getPrincipal();

        if (!optionalPost.isPresent()) {
            return new ResponseEntity<>(String.format("Пост с id: %d не найден", postId), HttpStatus.NOT_FOUND);
        }

        PostLike newPostLike = new PostLike(user);
        newPostLike.setPost(optionalPost.get());
        postLikeService.create(newPostLike);
        return new ResponseEntity<>(String.format("Пользователь с id: %d добавил лайк в пост с id: %d",
                user.getUserId(), postId), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Удаление лайка из поста авторизованным пользователем")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Лайк удален из пост")
    })
    @DeleteMapping("/post/{postId}/like")
    public ResponseEntity<String> deleteLikeFromPost(
            @ApiParam(value = "Id поста", example = "1")
            @PathVariable @NonNull Long postId) {

        Optional<Post> optionalPost = postService.getById(postId);
        User user = userService.getPrincipal();

        if (!optionalPost.isPresent()) {
            return new ResponseEntity<>(String.format("Пост с id: %d не найден", postId), HttpStatus.NOT_FOUND);
        }

        for (PostLike postLike : optionalPost.get().getPostLikes()) {
            if (postLike.getLike().getUser().getUserId().equals(user.getUserId())) {
                postLikeService.delete(postLike);
            }
        }
        return new ResponseEntity<>(String.format("Пользователь с id: %d удалил лайк из поста с id: %d",
                user.getUserId(), postId), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Добавление поста в закладки авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Пост добавлен в закладки")
    })
    @PostMapping("/post/{postId}/bookmark")
    public ResponseEntity<String> addPostToBookmark(
            @ApiParam(value = "Id поста", example = "1")
            @PathVariable @NonNull Long postId) {

        Optional<Post> optionalPost = postService.getById(postId);
        User user = userService.getPrincipal();

        if (!optionalPost.isPresent()) {
            return new ResponseEntity<>(String.format("Пост с id: %d не найден", postId), HttpStatus.NOT_FOUND);
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setPost(optionalPost.get());
        bookmarkService.create(bookmark);
        return new ResponseEntity<>(String.format("Пользователь с id: %d добавил пост с id: %d в закладки",
                user.getUserId(), postId), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Удаление поста из закладок авторизованным пользователем")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Пост из закладок удален")
    })
    @DeleteMapping("/post/{postId}/bookmark")
    public ResponseEntity<String> deletePostFromBookmark(
            @ApiParam(value = "Id поста", example = "1")
            @PathVariable @NonNull Long postId) {

        Optional<Post> optionalPost = postService.getById(postId);
        User user = userService.getPrincipal();

        if (!optionalPost.isPresent()) {
            return new ResponseEntity<>(String.format("Пост с id: %d не найден", postId), HttpStatus.NOT_FOUND);
        }

        for (Bookmark bookmark : optionalPost.get().getBookmarks()) {
            if (bookmark.getUser().getUserId().equals(user.getUserId())) {
                bookmarkService.delete(bookmark);
            }
        }
        return new ResponseEntity<>(String.format("Пользователь с id: %d удалил пост с id: %d из закладок",
                user.getUserId(), postId), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Репост поста авторизованным пользователем")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Репост добавлен в пост")
    })
    @PostMapping("/post/{postId}/repost")
    public ResponseEntity<String> addRepostToPost(
            @ApiParam(value = "Id поста", example = "1")
            @PathVariable @NonNull Long postId) {

        Optional<Post> optionalPost = postService.getById(postId);
        User user = userService.getPrincipal();

        if (!optionalPost.isPresent()) {
            return new ResponseEntity<>(String.format("Пост с id: %d не найден", postId), HttpStatus.NOT_FOUND);
        }

        Repost repost = new Repost();
        repost.setUser(user);
        repost.setPost(optionalPost.get());
        repostService.create(repost);
        return new ResponseEntity<>(String.format("Пользователь с id: %d сделал репост поста с id: %d ",
                user.getUserId(), postId), HttpStatus.CREATED);
    }
}
