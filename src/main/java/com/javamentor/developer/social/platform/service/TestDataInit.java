package com.javamentor.developer.social.platform.service;

import com.javamentor.developer.social.platform.models.entity.user.Active;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumAudioService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumImageService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumVideoService;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.GroupChatService;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.MessageService;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.SingleChatService;
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
import com.javamentor.developer.social.platform.service.abstracts.model.post.*;
import com.javamentor.developer.social.platform.service.abstracts.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * В этом классе необходимо выполнить инициализацию.
 * И заполнить базу данных случайными значениями.
 * Например:
 * - вызвать все методы по созданию пользователей и ролей
 *  createUser
 *  createChat - передать пользователя
 *  createRole - передать пользоваться
 *  и тд.
 */
@Component
public class TestDataInit {

    private AlbumAudioService albumAudioService;
    private AlbumImageService albumImageService;
    private AlbumService albumService;
    private AlbumVideoService albumVideoService;

    private GroupChatService groupChatService;
    private MessageService messageService;
    private SingleChatService singleChatService;

    private CommentService commentService;
    private MediaCommentService mediaCommentService;
    private PostCommentService postCommentService;

    private GroupCategoryService groupCategoryService;
    private GroupHasUserService groupHasUserService;
    private GroupService groupService;

    private CommentLikeService commentLikeService;
    private LikeService likeService;
    private MessageLikeService messageLikeService;
    private PostLikeService postLikeService;

    private AudiosService audiosService;
    private ImageService imageService;
    private MediaService mediaService;
    private PlaylistService playlistService;
    private VideosService videosService;

    private BookmarkService bookmarkService;
    private PostService postService;
    private RepostService repostService;
    private TagService tagService;
    private TopicService topicService;
    private UserTabsService userTabsService;

    private ActiveService activeService;
    private FollowerService followerService;
    private FriendService friendService;
    private LanguageService languageService;
    private RoleService roleService;
    private UserFriendsService userFriendsService;
    private UserService userService;

    private EntityManager entityManager;

    @Autowired
    public TestDataInit(AlbumAudioService albumAudioService, AlbumImageService albumImageService, AlbumService albumService, AlbumVideoService albumVideoService, GroupChatService groupChatService, MessageService messageService, SingleChatService singleChatService, CommentService commentService, MediaCommentService mediaCommentService, PostCommentService postCommentService, GroupCategoryService groupCategoryService, GroupHasUserService groupHasUserService, GroupService groupService, CommentLikeService commentLikeService, LikeService likeService, MessageLikeService messageLikeService, PostLikeService postLikeService, AudiosService audiosService, ImageService imageService, MediaService mediaService, PlaylistService playlistService, VideosService videosService, BookmarkService bookmarkService, PostService postService, RepostService repostService, TagService tagService, TopicService topicService, UserTabsService userTabsService, ActiveService activeService, FollowerService followerService, FriendService friendService, LanguageService languageService, RoleService roleService, UserFriendsService userFriendsService, UserService userService) {
        this.albumAudioService = albumAudioService;
        this.albumImageService = albumImageService;
        this.albumService = albumService;
        this.albumVideoService = albumVideoService;
        this.groupChatService = groupChatService;
        this.messageService = messageService;
        this.singleChatService = singleChatService;
        this.commentService = commentService;
        this.mediaCommentService = mediaCommentService;
        this.postCommentService = postCommentService;
        this.groupCategoryService = groupCategoryService;
        this.groupHasUserService = groupHasUserService;
        this.groupService = groupService;
        this.commentLikeService = commentLikeService;
        this.likeService = likeService;
        this.messageLikeService = messageLikeService;
        this.postLikeService = postLikeService;
        this.audiosService = audiosService;
        this.imageService = imageService;
        this.mediaService = mediaService;
        this.playlistService = playlistService;
        this.videosService = videosService;
        this.bookmarkService = bookmarkService;
        this.postService = postService;
        this.repostService = repostService;
        this.tagService = tagService;
        this.topicService = topicService;
        this.userTabsService = userTabsService;
        this.activeService = activeService;
        this.followerService = followerService;
        this.friendService = friendService;
        this.languageService = languageService;
        this.roleService = roleService;
        this.userFriendsService = userFriendsService;
        this.userService = userService;
    }

    public void createUsers(){
        LocalDate localDate = LocalDate.ofEpochDay(2008-05-30);
        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        entityManager.persist(roleAdmin);
        Role roleUser = new Role();
        roleUser.setName("USER");
        entityManager.persist(roleUser);
        Active active = new Active();
        active.setName("ACTIVE");
        entityManager.persist(active);
        Active disabled = new Active();
        disabled.setName("DISABLED");
        entityManager.persist(disabled);
        entityManager.flush();

        List<User> users = new ArrayList<>();
        User admin = new User();
        admin.setFirstName("Admin0");
        admin.setLastName("LastNameUser0");
        admin.setDateOfBirth(localDate);
        admin.setEducation("MIT University");
        admin.setAboutMe("My description about life - Admin0");
        admin.setAvatar("www.myavatar0.ru/9090");
        admin.setEmail("admin0@user.ru");
        admin.setPassword("userpass0");
        admin.setProfession("student");
        admin.setCity("SPb");
        admin.setLinkSite("www.mysite.ru");
        admin.setRole(roleAdmin);
        admin.setStatus("free");
        admin.setActive(active);
        users.add(admin);


        for(int i = 1; i < 100; i++) {
            User user = new User();
            user.setFirstName("User" + i);
            user.setLastName("LastNameUser" + i);
            user.setDateOfBirth(localDate);
            user.setEducation("MIT University");
            user.setAboutMe("My description about life - User" + i);
            user.setAvatar("www.myavatar" + i + ".ru/9090");
            user.setEmail("user" + i + "@user.ru");
            user.setPassword("userpass" + i);
            user.setProfession("student");
            user.setCity("SPb");
            user.setLinkSite("www.mysite.ru");
            user.setRole(roleUser);
            user.setStatus("free");
            if(i%2 == 0) {
                user.setActive(active);
            } else {
                user.setActive(disabled);
            }
            users.add(user);
        }

        for(User user: users) {
            userService.create(user);
        }
    }
}
