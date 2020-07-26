package com.javamentor.developer.social.platform.service.impl;

import com.javamentor.developer.social.platform.models.entity.chat.Chat;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import com.javamentor.developer.social.platform.models.entity.comment.Comment;
import com.javamentor.developer.social.platform.models.entity.comment.CommentType;
import com.javamentor.developer.social.platform.models.entity.comment.MediaComment;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import com.javamentor.developer.social.platform.models.entity.group.Group;
import com.javamentor.developer.social.platform.models.entity.group.GroupCategory;
import com.javamentor.developer.social.platform.models.entity.group.GroupHasUser;
import com.javamentor.developer.social.platform.models.entity.like.*;
import com.javamentor.developer.social.platform.models.entity.media.*;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.user.*;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.ChatService;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.MessageService;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.CommentService;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.MediaCommentService;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.PostCommentService;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupCategoryService;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupHasUserService;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupService;
import com.javamentor.developer.social.platform.service.abstracts.model.like.CommentLikeService;
import com.javamentor.developer.social.platform.service.abstracts.model.like.LikeService;
import com.javamentor.developer.social.platform.service.abstracts.model.like.MessageLikeService;
import com.javamentor.developer.social.platform.service.abstracts.model.like.PostLikeService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.*;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class TestDataInitService {

    private final LocalDateTime userLocalDate = LocalDateTime.of(2014,11,11,17,45);
    private final LocalDateTime userLocalDateNow = LocalDateTime.now();
    private final Date dateDateClass = new Date(1212121212121L);

    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private User user6;
    private User user7;
    private User user8;

    private Set<Media> mediaSet1 = new HashSet<>();
    private Set<Media> mediaSet2 = new HashSet<>();
    private Set<Media> mediaSet3 = new HashSet<>();
    private Set<Media> mediaSet4 = new HashSet<>();
    private Set<Media> mediaSet5 = new HashSet<>();

    private Media media1;
    private Media media2;
    private Media media3;
    private Media media4;
    private Media media5;
    private Media media6;

    private Chat chat1;
    private Chat chat2;
    private Chat chat3;

    private Post post1;
    private Post post2;
    private Post post3;
    private Post post4;

    private Group group1;
    private Group group2;
    private Group group3;

    private Comment comment1;
    private Comment comment2;
    private Comment comment3;

    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    private UserService userService;
    private ActiveService activeService;
    private FollowerService followerService;
    private FriendService friendService;
    private LanguageService languageService;
    private RoleService roleService;
    private StatusService statusService;
    private PostService postService;
    private AlbumService albumService;
    private AudiosService audiosService;
    private ImageService imageService;
    private MediaService mediaService;
    private VideosService videosService;
    private CommentLikeService commentLikeService;
    private LikeService likeService;
    private MessageLikeService messageLikeService;
    private PostLikeService postLikeService;
    private GroupCategoryService groupCategoryService;
    private GroupHasUserService groupHasUserService;
    private GroupService groupService;
    private CommentService commentService;
    private MediaCommentService mediaCommentService;
    private PostCommentService postCommentService;
    private ChatService chatService;
    private MessageService messageService;

    @Autowired
    public TestDataInitService(UserService userService,
                               ActiveService activeService,
                               FollowerService followerService,
                               FriendService friendService,
                               LanguageService languageService,
                               RoleService roleService,
                               StatusService statusService,
                               PostService postService,
                               AlbumService albumService,
                               AudiosService audiosService,
                               ImageService imageService,
                               MediaService mediaService,
                               VideosService videosService,
                               CommentLikeService commentLikeService,
                               LikeService likeService,
                               MessageLikeService messageLikeService,
                               PostLikeService postLikeService,
                               GroupCategoryService groupCategoryService,
                               GroupHasUserService groupHasUserService,
                               GroupService groupService,
                               CommentService commentService,
                               MediaCommentService mediaCommentService,
                               PostCommentService postCommentService,
                               ChatService chatService,
                               MessageService messageService) {
        this.userService = userService;
        this.activeService = activeService;
        this.followerService = followerService;
        this.friendService = friendService;
        this.languageService = languageService;
        this.roleService = roleService;
        this.statusService = statusService;
        this.postService = postService;
        this.albumService = albumService;
        this.audiosService = audiosService;
        this.imageService = imageService;
        this.mediaService = mediaService;
        this.videosService = videosService;
        this.commentLikeService = commentLikeService;
        this.likeService = likeService;
        this.messageLikeService = messageLikeService;
        this.postLikeService = postLikeService;
        this.groupCategoryService = groupCategoryService;
        this.groupHasUserService = groupHasUserService;
        this.groupService = groupService;
        this.commentService = commentService;
        this.mediaCommentService = mediaCommentService;
        this.postCommentService = postCommentService;
        this.chatService = chatService;
        this.messageService = messageService;
    }

    public void createEntity(){
        createUserEntity();
        createMediaEntity();
        createChatEntity();
        createMessageEntity();
        createAlbumEntity();
        createFriendEntity();
        createFollowerEntity();
        createPostEntity();
        createGroupEntity();
        createGroupHasUserEntity();
        createPostCommentEntity();
        createPostLikeEntity();
        createCommentLikeEntity();
        createMessageLikeEntity();
        createMediaCommentEntity();
        createAudiosEntity();
        createImageEntity();
        createVideosEntity();
        createPostMessageUserEntity();
    }

    private void createUserEntity(){
        Active active = Active.builder()
                .name("ACTIVE")
                .build();

        Active disabled = Active.builder()
                .name("DISABLED")
                .build();

        Language language = Language.builder()
                .name("Russian")
                .build();
        Language language2 = Language.builder()
                .name("English")
                .build();
        Set<Language> langSet = new HashSet<>();
        langSet.add(language);
        Set<Language> langSet2 = new HashSet<>();
        langSet2.add(language2);

        Role roleUser = Role.builder()
                .name("USER")
                .build();

        Role roleAdmin = Role.builder()
                .name("ADMIN")
                .build();

        Status status = Status.builder()
                .name("free")
                .build();

        Status status2 = Status.builder()
                .name("busy")
                .build();

        this.user1 = User.builder()
                .aboutMe("My description about life - Admin")
                .active(active)
                .avatar("www.myavatar.ru/9090")
                .city("SPb")
                .dateOfBirth(dateDateClass)
                .education("MIT University")
                .email("admin@admin.ru")
                .firstName("Admin1")
                .id_enable(true)
                .languages(langSet)
                .lastName("LastName")
                .lastRedactionDate(userLocalDateNow)
                .linkSite("www.mysite.ru")
                .messages(null)
                .password("rootpass")
                .persistDate(userLocalDate)
                .posts(null)
                .role(roleAdmin)
                .status(status)
                .build();
        userService.create(user1);

        this.user2 = User.builder()
                .aboutMe("My description about life - User1")
                .active(active)
                .avatar("www.myavatar.ru/9090")
                .city("SPb")
                .dateOfBirth(dateDateClass)
                .education("MIT University")
                .email("user1@user.email")
                .firstName("User1")
                .id_enable(true)
                .languages(langSet)
                .lastName("LastName")
                .lastRedactionDate(userLocalDateNow)
                .linkSite("www.mysite.ru")
                .messages(null)
                .password("userpass1")
                .persistDate(userLocalDate)
                .posts(null)
                .role(roleUser)
                .status(status)
                .build();
        userService.create(user2);

        this.user3 = User.builder()
                .aboutMe("My description about life - User2")
                .active(active)
                .avatar("www.myavatar.ru/9090")
                .city("SPb")
                .dateOfBirth(dateDateClass)
                .education("MIT University")
                .email("user2@user.email")
                .firstName("User2")
                .id_enable(true)
                .languages(langSet)
                .lastName("LastName")
                .lastRedactionDate(userLocalDateNow)
                .linkSite("www.mysite.ru")
                .messages(null)
                .password("userpass2")
                .persistDate(userLocalDate)
                .posts(null)
                .role(roleUser)
                .status(status2)
                .build();
        userService.create(user3);

        this.user4 = User.builder()
                .aboutMe("My description about life - User3")
                .active(disabled)
                .avatar("www.myavatar.ru/9090")
                .city("SPb")
                .dateOfBirth(dateDateClass)
                .education("MIT University")
                .email("user3@user.email")
                .firstName("User3")
                .id_enable(true)
                .languages(langSet2)
                .lastName("LastName")
                .lastRedactionDate(userLocalDateNow)
                .linkSite("www.mysite.ru")
                .messages(null)
                .password("userpass3")
                .persistDate(userLocalDate)
                .posts(null)
                .role(roleUser)
                .status(status)
                .build();
        userService.create(user4);

        this.user5 = User.builder()
                .aboutMe("My description about life - User4")
                .active(disabled)
                .avatar("www.myavatar.ru/9090")
                .city("SPb")
                .dateOfBirth(dateDateClass)
                .education("MIT University")
                .email("user4@user.email")
                .firstName("User4")
                .id_enable(true)
                .languages(langSet)
                .lastName("LastName")
                .lastRedactionDate(userLocalDateNow)
                .linkSite("www.mysite.ru")
                .messages(null)
                .password("userpass4")
                .persistDate(userLocalDate)
                .posts(null)
                .role(roleUser)
                .status(status)
                .build();
        userService.create(user5);

        this.user6 = User.builder()
                .aboutMe("My description about life - User5")
                .active(active)
                .avatar("www.myavatar.ru/9090")
                .city("SPb")
                .dateOfBirth(dateDateClass)
                .education("MIT University")
                .email("user5@user.email")
                .firstName("User5")
                .id_enable(true)
                .languages(langSet2)
                .lastName("LastName")
                .lastRedactionDate(userLocalDateNow)
                .linkSite("www.mysite.ru")
                .messages(null)
                .password("userpass5")
                .persistDate(userLocalDate)
                .posts(null)
                .role(roleUser)
                .status(status)
                .build();
        userService.create(user6);

        this.user7 = User.builder()
                .aboutMe("My description about life - User6")
                .active(disabled)
                .avatar("www.myavatar.ru/9090")
                .city("SPb")
                .dateOfBirth(dateDateClass)
                .education("MIT University")
                .email("user6@user.email")
                .firstName("User6")
                .id_enable(true)
                .languages(langSet)
                .lastName("LastName")
                .lastRedactionDate(userLocalDateNow)
                .linkSite("www.mysite.ru")
                .messages(null)
                .password("userpass6")
                .persistDate(userLocalDate)
                .posts(null)
                .role(roleUser)
                .status(status2)
                .build();
        userService.create(user7);

        this.user8 = User.builder()
                .aboutMe("My description about life - User7")
                .active(disabled)
                .avatar("www.myavatar.ru/9090")
                .city("SPb")
                .dateOfBirth(dateDateClass)
                .education("MIT University")
                .email("user7@user.email")
                .firstName("User7")
                .id_enable(true)
                .languages(langSet)
                .lastName("LastName")
                .lastRedactionDate(userLocalDateNow)
                .linkSite("www.mysite.ru")
                .messages(null)
                .password("userpass7")
                .persistDate(userLocalDate)
                .posts(null)
                .role(roleUser)
                .status(status)
                .build();
        userService.create(user8);
    }

    private void createMediaEntity(){
        this.media1 = Media.builder()
                .mediaType(MediaType.AUDIO)
                .persistDateTime(userLocalDateNow)
                .url("www.mymedia.ru")
                .user(this.user2)
                .build();

        this.media2 = Media.builder()
                .mediaType(MediaType.VIDEO)
                .persistDateTime(userLocalDateNow)
                .url("www.mymediavideo.ru")
                .user(this.user3)
                .build();

        this.media3 = Media.builder()
                .mediaType(MediaType.IMAGE)
                .persistDateTime(userLocalDateNow)
                .url("www.mymediavideo.ru")
                .user(this.user4)
                .build();

        this.media4 = Media.builder()
                .mediaType(MediaType.IMAGE)
                .persistDateTime(userLocalDateNow)
                .url("www.mymediavideo.ru")
                .user(this.user5)
                .build();

        this.media5 = Media.builder()
                .mediaType(MediaType.IMAGE)
                .persistDateTime(userLocalDateNow)
                .url("www.mymediavideo.ru")
                .user(this.user7)
                .build();

        this.media6 = Media.builder()
                .mediaType(MediaType.AUDIO)
                .persistDateTime(userLocalDateNow)
                .url("www.mymediavideo.ru")
                .user(this.user8)
                .build();

        this.mediaSet1.add(this.media1);
        this.mediaSet1.add(this.media2);
        this.mediaSet2.add(this.media3);
        this.mediaSet3.add(this.media4);
        this.mediaSet4.add(this.media5);
        this.mediaSet5.add(this.media6);
    }

    private void createChatEntity() {
        this.chat1 = Chat.builder().
                persistDate(userLocalDate)
                .title("The first init chat")
                .build();

        this.chat2 = Chat.builder().
                persistDate(userLocalDate)
                .title("The second init chat")
                .build();

        this.chat3 = Chat.builder().
                persistDate(userLocalDate)
                .title("The third init chat")
                .build();
    }

    private void createMessageEntity() {
        this.message1 = Message.builder()
                .message("Test init message1")
                .is_unread(true)
                .chat(this.chat1)
                .lastRedactionDate(userLocalDateNow)
                .media(this.mediaSet1)
                .userSender(this.user2)
                .persistDate(userLocalDate)
                .build();

        this.message2 = Message.builder()
                .message("Test init message2")
                .is_unread(true)
                .chat(this.chat1)
                .lastRedactionDate(userLocalDateNow)
                .media(this.mediaSet2)
                .userSender(this.user3)
                .persistDate(userLocalDate)
                .build();

        this.message3 = Message.builder()
                .message("Test init message3")
                .is_unread(true)
                .chat(this.chat2)
                .lastRedactionDate(userLocalDateNow)
                .media(this.mediaSet4)
                .userSender(this.user4)
                .persistDate(userLocalDate)
                .build();

        this.message4 = Message.builder()
                .message("Test init message4")
                .is_unread(true)
                .chat(this.chat2)
                .lastRedactionDate(userLocalDateNow)
                .media(this.mediaSet5)
                .userSender(this.user6)
                .persistDate(userLocalDate)
                .build();

        this.message5 = Message.builder()
                .message("Test init message5")
                .is_unread(true)
                .chat(this.chat3)
                .lastRedactionDate(userLocalDateNow)
                .media(this.mediaSet3)
                .userSender(this.user8)
                .persistDate(userLocalDate)
                .build();

        messageService.create(message1);
        messageService.create(message2);
        messageService.create(message3);
        messageService.create(message4);
        messageService.create(message5);
    }

    private void createAlbumEntity(){
        albumService.create(Album.builder().media(this.media1).build());
        albumService.create(Album.builder().media(this.media2).build());
        albumService.create(Album.builder().media(this.media3).build());
    }

    private void createFriendEntity(){
        Friend friend1 = Friend.builder().user(user2).friend(user3).build();
        Friend friend2 = Friend.builder().user(user2).friend(user4).build();
        Friend friend3 = Friend.builder().user(user3).friend(user6).build();
        Friend friend4 = Friend.builder().user(user4).friend(user7).build();
        Friend friend5 = Friend.builder().user(user5).friend(user7).build();
        Friend friend6 = Friend.builder().user(user6).friend(user7).build();

        friendService.create(friend1);
        friendService.create(friend2);
        friendService.create(friend3);
        friendService.create(friend4);
        friendService.create(friend5);
        friendService.create(friend6);
    }

    private void createFollowerEntity(){
        Follower follower1 = Follower.builder().user(user2).follower(user5).build();
        Follower follower2 = Follower.builder().user(user2).follower(user6).build();
        Follower follower3 = Follower.builder().user(user8).follower(user2).build();
        Follower follower4 = Follower.builder().user(user5).follower(user4).build();
        Follower follower5 = Follower.builder().user(user6).follower(user5).build();

        followerService.create(follower1);
        followerService.create(follower2);
        followerService.create(follower3);
        followerService.create(follower4);
        followerService.create(follower5);
    }

    private void createPostEntity(){
        this.post1 = Post.builder()
                .persistDate(userLocalDate)
                .lastRedactionDate(userLocalDateNow)
                .media(mediaSet4)
                .text("There is the first text of this post")
                .title("The first test post")
                .user(user3)
                .build();

        this.post2 = Post.builder()
                .persistDate(userLocalDate)
                .lastRedactionDate(userLocalDateNow)
                .media(mediaSet1)
                .text("There is the second text of this post")
                .title("The second test post")
                .user(user4)
                .build();

        this.post3 = Post.builder()
                .persistDate(userLocalDate)
                .lastRedactionDate(userLocalDateNow)
                .media(mediaSet5)
                .text("There is the third text of this post")
                .title("The third test post")
                .user(user3)
                .build();

        this.post4 = Post.builder()
                .persistDate(userLocalDate)
                .lastRedactionDate(userLocalDateNow)
                .media(mediaSet3)
                .text("There is the fourth text of this post")
                .title("The fourth test post")
                .user(user6)
                .build();
    }

    private void createGroupEntity(){
        GroupCategory groupCategory1 = GroupCategory.builder().category("Programming").build();
        GroupCategory groupCategory2 = GroupCategory.builder().category("Flowers").build();
        Set<Post> postSet1 = new HashSet<>();
        Set<Post> postSet2 = new HashSet<>();
        Set<Post> postSet3 = new HashSet<>();
        postSet1.add(this.post1);
        postSet1.add(this.post2);
        postSet2.add(this.post3);
        postSet3.add(this.post4);

        this.group1 = Group.builder()
                .groupCategory(groupCategory1)
                .lastRedactionDate(userLocalDateNow)
                .linkSite("www.groupsite.ru")
                .name("JAVA IS")
                .persistDate(userLocalDate)
                .posts(postSet1)
                .build();
        groupService.create(group1);

        this.group2 = Group.builder()
                .groupCategory(groupCategory2)
                .lastRedactionDate(userLocalDateNow)
                .linkSite("www.groupsite.ru")
                .name("FLOWER IS")
                .persistDate(userLocalDate)
                .posts(postSet2)
                .build();
        groupService.create(group2);

        this.group3 = Group.builder()
                .groupCategory(groupCategory2)
                .lastRedactionDate(userLocalDateNow)
                .linkSite("www.groupsite.ru")
                .name("TEST GROUP")
                .persistDate(userLocalDate)
                .posts(postSet3)
                .build();
        groupService.create(group3);
    }

    private void createGroupHasUserEntity(){
        GroupHasUser groupHasUser1 = GroupHasUser.builder()
                .group(group1)
                .user(user3)
                .persistDate(userLocalDate)
                .build();
        groupHasUserService.create(groupHasUser1);

        GroupHasUser groupHasUser2 = GroupHasUser.builder()
                .group(group1)
                .user(user4)
                .persistDate(userLocalDate)
                .build();
        groupHasUserService.create(groupHasUser2);

        GroupHasUser groupHasUser3 = GroupHasUser.builder()
                .group(group2)
                .user(user7)
                .persistDate(userLocalDate)
                .build();
        groupHasUserService.create(groupHasUser3);

        GroupHasUser groupHasUser4 = GroupHasUser.builder()
                .group(group3)
                .user(user8)
                .persistDate(userLocalDate)
                .build();
        groupHasUserService.create(groupHasUser4);
    }

    private void createPostCommentEntity(){
        this.comment1 = Comment.builder()
                .comment("Test comment 1")
                .commentType(CommentType.MEDIA)
                .lastRedactionDate(userLocalDate)
                .persistDate(userLocalDate)
                .user(user2)
                .build();
        PostComment postComment = PostComment.builder().post(post1).comment(comment1).build();
        postCommentService.create(postComment);

        this.comment2 = Comment.builder()
                .comment("Test comment 2")
                .commentType(CommentType.MEDIA)
                .lastRedactionDate(userLocalDate)
                .persistDate(userLocalDate)
                .user(user5)
                .build();
        PostComment postComment2 = PostComment.builder().post(post2).comment(comment2).build();
        postCommentService.create(postComment2);

        this.comment3 = Comment.builder()
                .comment("Test comment 3")
                .commentType(CommentType.MEDIA)
                .lastRedactionDate(userLocalDate)
                .persistDate(userLocalDate)
                .user(user4)
                .build();
        PostComment postComment3 = PostComment.builder().post(post3).comment(comment3).build();
        postCommentService.create(postComment3);
    }

    private void createPostLikeEntity(){
        Like like1 = Like.builder().likeType(LikeType.POST).user(user4).build();
        Like like2 = Like.builder().likeType(LikeType.POST).user(user6).build();
        Like like3 = Like.builder().likeType(LikeType.POST).user(user2).build();

        PostLike postLike1 = PostLike.builder().post(post1).like(like1).user(user4).build();
        postLikeService.create(postLike1);

        PostLike postLike2 = PostLike.builder().post(post1).like(like2).user(user6).build();
        postLikeService.create(postLike2);

        PostLike postLike3 = PostLike.builder().post(post3).like(like3).user(user2).build();
        postLikeService.create(postLike3);
    }

    private void createCommentLikeEntity(){
        Like like1 = Like.builder().likeType(LikeType.COMMENT).user(user2).build();
        Like like2 = Like.builder().likeType(LikeType.COMMENT).user(user5).build();
        Like like3 = Like.builder().likeType(LikeType.COMMENT).user(user8).build();

        CommentLike commentLike1 = CommentLike.builder()
                .like(like1)
                .comment(comment1)
                .user(user2)
                .build();
        commentLikeService.create(commentLike1);

        CommentLike commentLike2 = CommentLike.builder()
                .like(like2)
                .comment(comment2)
                .user(user5)
                .build();
        commentLikeService.create(commentLike2);

        CommentLike commentLike3 = CommentLike.builder()
                .like(like3)
                .comment(comment1)
                .user(user8)
                .build();
        commentLikeService.create(commentLike3);
    }
    private void createMessageLikeEntity(){
        Like like1 = Like.builder().likeType(LikeType.MESSAGE).user(user3).build();
        Like like2 = Like.builder().likeType(LikeType.MESSAGE).user(user7).build();
        Like like3 = Like.builder().likeType(LikeType.MESSAGE).user(user6).build();

        MessageLike messageLike1 = MessageLike.builder()
                .like(like1)
                .message(message1)
                .user(user3)
                .build();
        messageLikeService.create(messageLike1);

        MessageLike messageLike2 = MessageLike.builder()
                .like(like2)
                .message(message4)
                .user(user7)
                .build();
        messageLikeService.create(messageLike2);

        MessageLike messageLike3 = MessageLike.builder()
                .like(like3)
                .message(message5)
                .user(user6)
                .build();
        messageLikeService.create(messageLike3);
    }

    private void createMediaCommentEntity(){
        Comment comment1 = Comment.builder()
                .comment("Test comment Media 1")
                .commentType(CommentType.MEDIA)
                .lastRedactionDate(userLocalDate)
                .persistDate(userLocalDate)
                .user(user4)
                .build();
        MediaComment mediaComment1 = MediaComment.builder().comment(comment1).media(media1).build();
        mediaCommentService.create(mediaComment1);

        Comment comment2 = Comment.builder()
                .comment("Test comment Media 2")
                .commentType(CommentType.MEDIA)
                .lastRedactionDate(userLocalDate)
                .persistDate(userLocalDate)
                .user(user2)
                .build();
        MediaComment mediaComment2 = MediaComment.builder().comment(comment2).media(media1).build();
        mediaCommentService.create(mediaComment2);

        Comment comment3 = Comment.builder()
                .comment("Test comment Media 3")
                .commentType(CommentType.MEDIA)
                .lastRedactionDate(userLocalDate)
                .persistDate(userLocalDate)
                .user(user7)
                .build();
        MediaComment mediaComment3 = MediaComment.builder().comment(comment3).media(media1).build();
        mediaCommentService.create(mediaComment3);
    }

    private void createAudiosEntity(){
       audiosService.create(Audios.builder()
                .author("Test Author1")
                .icon("TestIcon1")
                .name("AudioTestName1")
                .media(media1)
                .build());

       audiosService.create(Audios.builder()
                .author("Test Author2")
                .icon("TestIcon2")
                .name("AudioTestName2")
                .media(media6)
                .build());
    }

    private void createImageEntity(){
        imageService.create(Image.builder()
                .description("Image Media Teat File 1")
                .media(media3)
                .build());

        imageService.create(Image.builder()
                .description("Image Media Teat File 2")
                .media(media4)
                .build());

        imageService.create(Image.builder()
                .description("Image Media Teat File 3")
                .media(media5)
                .build());
    }

    private void createVideosEntity(){
        videosService.create(Videos.builder()
                .icon("New Test Icon")
                .name("The first test media")
                .media(media2)
                .build());

    }

    private void createPostMessageUserEntity() {
        Set<Post> postSet = new HashSet<>();
        postSet.add(post1);
        postSet.add(post2);
        Set<Message> messageSet = new HashSet<>();
        messageSet.add(message1);
        messageSet.add(message2);
        user2.setPosts(postSet);
        user2.setMessages(messageSet);
        userService.update(user2);

        Set<Post> postSet2 = new HashSet<>();
        postSet2.add(post3);
        Set<Message> messageSet2 = new HashSet<>();
        messageSet2.add(message4);
        messageSet2.add(message5);
        user5.setPosts(postSet2);
        user5.setMessages(messageSet2);
        userService.update(user5);
    }
}
