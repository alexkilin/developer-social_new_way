package com.javamentor.developer.social.platform.webapp.controllers.v2;

import com.javamentor.developer.social.platform.models.dto.PostCreateDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import com.javamentor.developer.social.platform.models.entity.post.Bookmark;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.post.Repost;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.CommentService;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.PostCommentService;
import com.javamentor.developer.social.platform.service.abstracts.model.like.LikeService;
import com.javamentor.developer.social.platform.service.abstracts.model.like.PostLikeService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.BookmarkService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.RepostService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.UserTabsService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v2/post", produces = "application/json;charset=UTF-8")
@Api(value = "PostApi-v2", description = "Операции над постами пользователя")
@Validated
public class PostControllerV2 {
    private final PostDtoService postDtoService;
    private final PostConverter postConverter;
    private final PostService postService;
    private final UserService userService;
    private final UserTabsService userTabsService;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;
    private final BookmarkService bookmarkService;
    private final RepostService repostService;
    private final CommentService commentService;
    private final LikeService likeService;

    @Autowired
    public PostControllerV2(PostDtoService postDtoService,
                            PostConverter postConverter,
                            PostService postService,
                            UserService userService,
                            UserTabsService userTabsService,
                            PostCommentService postCommentService,
                            PostLikeService postLikeService,
                            BookmarkService bookmarkService,
                            RepostService repostService,
                            CommentService commentService,
                            LikeService likeService) {

        this.postDtoService = postDtoService;
        this.postConverter = postConverter;
        this.postService = postService;
        this.userService = userService;
        this.userTabsService = userTabsService;
        this.postCommentService = postCommentService;
        this.postLikeService = postLikeService;
        this.bookmarkService = bookmarkService;
        this.repostService = repostService;
        this.commentService = commentService;
        this.likeService = likeService;
    }


    @ApiOperation(value = "Получение списка всех постов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены", responseContainer = "List", response = PostDto.class)})
    @GetMapping(value = "/posts", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<PostDto, ?>> getAllPosts(@ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
                                                        @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        Long userPrincipalId = userService.getPrincipal().getUserId();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userPrincipalId", userPrincipalId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(postDtoService.getAllPosts(parameters));
    }

    @ApiOperation(value = "Получение поста по тэгу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены", response = PostDto.class, responseContainer = "List")})
    @GetMapping(value = "/posts", params = {"tag", "currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<PostDto, ?>> getPostsByTag(
            @ApiParam(value = "Название тэга", example = "Some tag") @RequestParam("tag") String tagText,
            @ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
            @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        Long userPrincipalId = userService.getPrincipal().getUserId();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userPrincipalId", userPrincipalId);
        parameters.put("tagText", tagText);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok(postDtoService.getPostsByTag(parameters));
    }

    @ApiOperation(value = "Получение всех существующих тегов")
    @ApiResponses(value =  {
            @ApiResponse(code = 200, message = "Теги получены", responseContainer = "List", response = TagDto.class)})
    @GetMapping(value = "/posts/tags", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<Object, Object>> getAllTags(@ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
                                                   @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok(postDtoService.getAllTags(parameters));
    }


    @ApiOperation(value = "Получение списка постов пользователя по его ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты получены", response = PostDto.class, responseContainer = "List")})
    @GetMapping(value = "/posts/user/{id}", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<PostDto, ?>> getPostsByUserId(
            @ApiParam(value = "ID пользователя", example = "60") @PathVariable("id") Long userId,
            @ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
            @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {

        Long userPrincipalId = userService.getPrincipal().getUserId();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userPrincipalId", userPrincipalId);
        parameters.put("userId", userId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok(postDtoService.getPostsByUserId(parameters));
    }

    @ApiOperation(value = "Добавление поста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пост добавлен", response = PostDto.class)
    })
    @PostMapping("/post")
    @Validated(OnCreate.class)
    public ResponseEntity<PostDto> addPost(
            @ApiParam(value = "Объект добавляемого поста") @RequestBody @Valid @NotNull PostCreateDto postCreateDto) {
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
    public ResponseEntity<?> deletePost(
            @ApiParam(value = "ID поста", example = "20") @PathVariable @NotNull Long id) {
        Optional<Post> result = postService.getById(id);
        if (result.isPresent()) {
            Post post = result.get();
            userTabsService.deletePost(post);
            return ResponseEntity.ok().body(String.format("Deleted Post with ID %d, is successful", id));
        }
        return ResponseEntity.badRequest().body(String.format("Can't find Post with ID %d", id));
    }

    @ApiOperation(value = "Получение всех комментариев поста по id поста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Комментарии получены", responseContainer = "List",
                    response = CommentDto.class)})
    @GetMapping(value = "/post/{postId}/comments", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<Object, Object>> showPostComments(
            @ApiParam(value = "ID поста", example = "20") @PathVariable("postId") Long postId,
            @ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
            @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("postId", postId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok(postDtoService.getCommentsByPostId(parameters));
    }

    @ApiOperation(value = "Добавление комментария к посту")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Комментарий добавлен"),
            @ApiResponse(code = 404, message = "Пользователь или пост не найдены")
    })
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<?> addCommentToPost(
            @ApiParam(value = "Комментарий к посту") @RequestBody String comment,
            @ApiParam(value = "Идентификатор поста", example = "1") @PathVariable @NonNull Long postId) {
        User user = userService.getPrincipal();
        PostComment postComment = new PostComment(comment, user);
        commentService.create(postComment.getComment());
        Post post = Post.builder().id(postId).build();
        postComment.setPost(post);
        postCommentService.create(postComment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postDtoService.getPostById(postId, user.getUserId()));
    }

    @ApiOperation(value = "Добавление лайка посту авторизованным пользователем")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Лайк добавлен в пост")
    })
    @PostMapping("/post/{postId}/like")
    public ResponseEntity<?> addLikeToPost(
            @ApiParam(value = "Id поста", example = "1") @PathVariable @NonNull Long postId) {
        User user = userService.getPrincipal();

        Optional<PostLike> optionalPostLike = postLikeService.getPostLikeByPostIdAndUserId(postId, user.getUserId());
        if (optionalPostLike.isPresent()) {
            return ResponseEntity.badRequest().body("The Like has already been added");
        }

        Post post = Post.builder().id(postId).build();
        PostLike postLike = new PostLike(user);
        likeService.create(postLike.getLike());
        postLike.setPost(post);
        postLikeService.create(postLike);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postDtoService.getPostById(postId, user.getUserId()));
    }

    @ApiOperation(value = "Удаление лайка из поста авторизованным пользователем")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Лайк удален из пост")
    })
    @DeleteMapping("/post/{postId}/like")
    public ResponseEntity<?> deleteLikeFromPost(
            @ApiParam(value = "Id поста", example = "1")
            @PathVariable @NonNull Long postId) {
        User user = userService.getPrincipal();
        Optional<PostLike> optionalLike =
                postLikeService.getPostLikeByPostIdAndUserId(postId, user.getUserId());

        if(!optionalLike.isPresent()) {
            return ResponseEntity.badRequest().body("The Like already been removed");
        }

        PostLike postLike = optionalLike.get();
        postLikeService.delete(postLike);
        return ResponseEntity.ok().body(postDtoService.getPostById(postId, user.getUserId()));
    }

    @ApiOperation(value = "Добавление поста в закладки авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Пост добавлен в закладки")
    })
    @PostMapping("/post/{postId}/bookmark")
    public ResponseEntity<?> addPostToBookmark(
            @ApiParam(value = "Id поста", example = "1") @PathVariable @NonNull Long postId) {
        User user = userService.getPrincipal();

        Optional<Bookmark> optionalBookmark = bookmarkService.getBookmarkByPostIdAndUserId(postId, user.getUserId());
        if(optionalBookmark.isPresent()) {
            return ResponseEntity.badRequest().body("The Post has already been added to the bookmark");
        }

        Post post = Post.builder().id(postId).build();
        Bookmark bookmark = Bookmark.builder().user(user).post(post).build();
        bookmarkService.create(bookmark);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postDtoService.getPostById(postId, user.getUserId()));
    }

    @ApiOperation(value = "Удаление поста из закладок авторизованным пользователем")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Пост из закладок удален")
    })
    @DeleteMapping("/post/{postId}/bookmark")
    public ResponseEntity<?> deletePostFromBookmark(
            @ApiParam(value = "Id поста", example = "1") @PathVariable @NonNull Long postId) {
        User user = userService.getPrincipal();

        Optional<Bookmark> optionalBookmark = bookmarkService.getBookmarkByPostIdAndUserId(postId, user.getUserId());
        if(!optionalBookmark.isPresent()) {
            return ResponseEntity.badRequest().body("The Post has already been removed from the bookmark");
        }

        bookmarkService.deleteBookmarkByPostIdAndUserId(postId, user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postDtoService.getPostById(postId, user.getUserId()));
    }

    @ApiOperation(value = "Репост поста авторизованным пользователем")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Репост добавлен в пост")
    })
    @PostMapping("/post/{postId}/repost")
    public ResponseEntity<?> addRepostToPost(
            @ApiParam(value = "Id поста", example = "1") @PathVariable @NonNull Long postId) {
        User user = userService.getPrincipal();
        Post post = Post.builder().id(postId).build();
        Repost repost = Repost.builder().user(user).post(post).build();
        repostService.create(repost);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postDtoService.getPostById(postId, user.getUserId()));
    }

    @ApiOperation(value = "Получение закладок авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все посты получены")
    })
    @GetMapping(value = "/posts/bookmarks", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity <PageDto<PostDto, ?>> getAllBookmarkedPosts(@ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
                                                                @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage){
        Long userPrincipalId = userService.getPrincipal().getUserId();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userPrincipalId", userPrincipalId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(postDtoService.getAllBookmarkedPosts(parameters));
    }

}
