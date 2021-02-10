package com.javamentor.developer.social.platform.service;

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
}
