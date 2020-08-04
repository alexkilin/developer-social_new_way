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
import com.javamentor.developer.social.platform.models.entity.post.Tag;
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
import com.javamentor.developer.social.platform.service.abstracts.model.post.TagService;
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

    private final LocalDateTime userLocalDate = LocalDateTime.of(2014, 11, 11, 17, 45);
    private final LocalDateTime userLocalDateNow = LocalDateTime.now();
    private final Date dateDateClass = new Date(1212121212121L);

    // You can change size of init data by variable "k"
    private int k = 1;
    private final int numOfUsers = 100 * k;
    private final int numOfMedias = 100 * k;
    private final int numOfChats = 20 * k;
    private final int numOfMessages = 100 * k;
    private final int numOfAlbums = 20 * k;
    private final int numOfFriends = 500 * k;
    private final int numOfFollowers = 500 * k;
    private final int numOfPosts = 100 * k;
    private final int numOfGroups = 20 * k;
    private final int numOfPostComments = 100 * k;
    private final int numOfPostLikes = 100 * k;
    private final int numOfCommentLikes = 100 * k;
    private final int numOfMessagesLikes = 100 * k;
    private final int numOfMediaComments = 100 * k;
    private final int numOfTags = 100 * k;

    private User[] users = new User[numOfUsers];
    private Media[] medias = new Media[numOfMedias];
    private Chat[] chats = new Chat[numOfChats];
    private Message[] messages = new Message[numOfMessages];
    private Post[] posts = new Post[numOfPosts];
    private Group[] groups = new Group[numOfGroups];
    private Comment[] postComments = new Comment[numOfPostComments];
    private Like[] postLikes = new Like[numOfPostLikes];
    private Like[] commentLikes = new Like[numOfCommentLikes];
    private Like[] messageLikes = new Like[numOfMessagesLikes];
    private Comment[] mediaComments = new Comment[numOfMediaComments];
    private Tag[] tags = new Tag[numOfTags];

    private UserService userService;
    private FollowerService followerService;
    private FriendService friendService;
    private AlbumService albumService;
    private AudiosService audiosService;
    private ImageService imageService;
    private VideosService videosService;
    private CommentLikeService commentLikeService;
    private MessageLikeService messageLikeService;
    private PostLikeService postLikeService;
    private GroupHasUserService groupHasUserService;
    private GroupService groupService;
    private MediaCommentService mediaCommentService;
    private PostCommentService postCommentService;
    private MessageService messageService;
    private TagService tagService;

    @Autowired
    public TestDataInitService(UserService userService,
                               FollowerService followerService,
                               FriendService friendService,
                               AlbumService albumService,
                               AudiosService audiosService,
                               ImageService imageService,
                               VideosService videosService,
                               CommentLikeService commentLikeService,
                               MessageLikeService messageLikeService,
                               PostLikeService postLikeService,
                               GroupHasUserService groupHasUserService,
                               GroupService groupService,
                               MediaCommentService mediaCommentService,
                               PostCommentService postCommentService,
                               MessageService messageService, TagService tagService) {
        this.userService = userService;
        this.followerService = followerService;
        this.friendService = friendService;
        this.albumService = albumService;
        this.audiosService = audiosService;
        this.imageService = imageService;
        this.videosService = videosService;
        this.commentLikeService = commentLikeService;
        this.messageLikeService = messageLikeService;
        this.postLikeService = postLikeService;
        this.groupHasUserService = groupHasUserService;
        this.groupService = groupService;
        this.mediaCommentService = mediaCommentService;
        this.postCommentService = postCommentService;
        this.messageService = messageService;
        this.tagService = tagService;
    }

    public void createEntity() {
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
        createTagEntity();
    }


    private void createUserEntity() {
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

        String name;
        String emailName;
        Active activityTest;
        Set<Language> languageTestSet;
        Role role;
        Status statusTest;

        for (int i = 0; i != numOfUsers; i++) {
            if (i == 0) {
                name = "Admin";
                emailName = "admin";
                activityTest = active;
                languageTestSet = langSet;
                role = roleAdmin;
                statusTest = status;
            } else if (i % 2 == 0) {
                name = "User";
                emailName = "user";
                activityTest = active;
                languageTestSet = langSet;
                role = roleUser;
                statusTest = status;
            } else {
                name = "User";
                emailName = "user";
                activityTest = disabled;
                languageTestSet = langSet2;
                role = roleUser;
                statusTest = status2;
            }
            users[i] = User.builder()
                    .aboutMe("My description about life - " + name + i)
                    .active(activityTest)
                    .avatar("www.myavatar" + i + ".ru/9090")
                    .city("SPb")
                    .dateOfBirth(dateDateClass)
                    .education("MIT University")
                    .email(emailName + i + "@user.ru")
                    .firstName(name + i)
                    .isEnable(true)
                    .languages(languageTestSet)
                    .lastName("LastNameUser" + i)
                    .lastRedactionDate(userLocalDateNow)
                    .linkSite("www.mysite.ru")
                    .messages(null)
                    .password("userpass" + i)
                    .persistDate(userLocalDate)
                    .posts(null)
                    .role(role)
                    .status(statusTest)
                    .build();
            userService.create(users[i]);
        }
    }

    private void createMediaEntity() {
        MediaType mediaType;
        String url;
        for (int i = 0; i != numOfMedias; i++) {
            if (i % 5 == 0) {
                mediaType = MediaType.AUDIO;
                url = "www.myaudio.ru";
            } else if (i % 3 == 0) {
                mediaType = MediaType.VIDEO;
                url = "www.myvideo.ru";
            } else {
                mediaType = MediaType.IMAGE;
                url = "www.myimage.ru";
            }
            medias[i] = Media.builder()
                    .mediaType(mediaType)
                    .persistDateTime(userLocalDateNow)
                    .url(url)
                    .user(users[i])
                    .build();
        }
    }

    private void createTagEntity() {
        for (int i = 0; i != numOfTags; i++) {
            tags[i] = Tag.builder()
                    .text("This is " + i + " tag")
                    .build();
        }
    }

    private void createChatEntity() {
        for (int i = 0; i != numOfChats; i++) {
            chats[i] = Chat.builder().
                    persistDate(userLocalDate)
                    .title(i + " init chat")
                    .build();
        }
    }

    private void createMessageEntity() {
        int num = 0;
        int startNum = numOfMessages / numOfChats;
        for (int j = 0; j != numOfChats; j++) {
            for (int i = num; i != startNum + num; i++) {
                Set<Media> mediaSet = new HashSet<>();
                mediaSet.add(medias[i]);
                messages[i] = Message.builder()
                        .message("Test init message" + i)
                        .is_unread(true)
                        .chat(chats[j])
                        .lastRedactionDate(userLocalDateNow)
                        .media(mediaSet)
                        .userSender(users[i])
                        .persistDate(userLocalDate)
                        .build();
                messageService.create(messages[i]);
            }
            num += startNum;
        }
    }

    private void createAlbumEntity() {
        int num = 0;
        int startNum = numOfMedias / numOfAlbums;
        for (int j = 0; j != numOfAlbums; j++) {
            for (int i = num; i != num + startNum; i++) {
                albumService.create(Album.builder().media(medias[i]).build());
            }
            num += startNum;
        }
    }

    private void createFriendEntity() {
        for (int i = 0; i != numOfFriends; i++) {
            friendService.create(Friend.builder()
                    .user(users[(int) (Math.random() * numOfUsers)])
                    .friend(users[(int) (Math.random() * numOfUsers)])
                    .build());
        }
    }

    private void createFollowerEntity() {
        for (int i = 0; i != numOfFollowers; i++) {
            followerService.create(Follower.builder()
                    .user(users[(int) (Math.random() * numOfUsers)])
                    .follower(users[(int) (Math.random() * numOfUsers)])
                    .build());
        }
    }

    private void createPostEntity() {
        for (int i = 0; i != numOfPosts; i++) {
            Set<Media> mediaSet = new HashSet<>();
            mediaSet.add(medias[i]);
            posts[i] = Post.builder()
                    .persistDate(userLocalDate)
                    .lastRedactionDate(userLocalDateNow)
                    .media(mediaSet)
                    .text("There is the " + i + " text of this post")
                    .title("The " + i + " test post")
                    .user(users[(int) (Math.random() * numOfUsers)])
                    .build();
        }
    }


    private void createGroupEntity() {
        GroupCategory groupCategory1 = GroupCategory.builder().category("Programming").build();
        GroupCategory groupCategory2 = GroupCategory.builder().category("Flowers").build();
        GroupCategory groupCategory;
        int num = 0;
        int startNum = numOfPosts / numOfGroups;
        for (int i = 0; i != numOfGroups; i++) {
            if (i % 2 == 0) {
                groupCategory = groupCategory1;
            } else {
                groupCategory = groupCategory2;
            }
            Set<Post> postSet = new HashSet<>();
            for (int j = num; j != num + startNum; j++) {
                postSet.add(posts[j]);
            }
            groups[i] = Group.builder()
                    .groupCategory(groupCategory)
                    .lastRedactionDate(userLocalDateNow)
                    .linkSite("www.groupsite" + i + ".ru")
                    .name("JAVA IS " + i)
                    .persistDate(userLocalDate)
                    .posts(postSet)
                    .description("This is a description of the group #" + i)
                    .user(users[(int) (Math.random() * numOfUsers)])
                    .build();
            groupService.create(groups[i]);
            num += startNum;
        }
    }

    private void createGroupHasUserEntity() {
        for (int i = 0; i != numOfUsers; i++) {
            groupHasUserService.create(GroupHasUser.builder()
                    .group(groups[(int) (Math.random() * numOfGroups / 2)])
                    .user(users[i])
                    .persistDate(userLocalDate)
                    .build());
        }
    }

    private void createPostCommentEntity() {
        for (int i = 0; i != numOfPostComments; i++) {
            postComments[i] = Comment.builder()
                    .comment("Test comment POST " + i)
                    .commentType(CommentType.POST)
                    .lastRedactionDate(userLocalDate)
                    .persistDate(userLocalDate)
                    .user(users[(int) (Math.random() * numOfUsers)])
                    .build();
            postCommentService.create(PostComment.builder()
                    .post(posts[(int) (Math.random() * numOfPosts)])
                    .comment(postComments[i])
                    .build());
        }
    }

    private void createPostLikeEntity() {
        for (int i = 0; i != numOfPostLikes; i++) {
            postLikes[i] = Like.builder()
                    .likeType(LikeType.POST)
                    .user(users[(int) (Math.random() * numOfUsers)])
                    .build();
            postLikeService.create(PostLike.builder()
                    .post(posts[(int) (Math.random() * numOfPosts)])
                    .like(postLikes[i])
                    .user(users[(int) (Math.random() * numOfUsers)])
                    .build());
        }
    }

    private void createCommentLikeEntity() {
        for (int i = 0; i != numOfCommentLikes; i++) {
            commentLikes[i] = Like.builder()
                    .likeType(LikeType.COMMENT)
                    .user(users[(int) (Math.random() * numOfUsers)])
                    .build();
            commentLikeService.create(CommentLike.builder()
                    .like(commentLikes[i])
                    .comment(postComments[(int) (Math.random() * numOfPostComments)])
                    .user(users[(int) (Math.random() * numOfUsers)])
                    .build());
        }
    }

    private void createMessageLikeEntity() {
        for (int i = 0; i != numOfMessagesLikes; i++) {
            messageLikes[i] = Like.builder()
                    .likeType(LikeType.MESSAGE)
                    .user(users[(int) (Math.random() * numOfUsers)])
                    .build();
            messageLikeService.create(MessageLike.builder()
                    .like(messageLikes[i])
                    .message(messages[(int) (Math.random() * numOfMessages)])
                    .user(users[(int) (Math.random() * numOfUsers)])
                    .build());
        }
    }

    private void createMediaCommentEntity() {
        for (int i = 0; i != numOfMediaComments; i++) {
            mediaComments[i] = Comment.builder()
                    .comment("Test comment MEDIA " + i)
                    .commentType(CommentType.MEDIA)
                    .lastRedactionDate(userLocalDate)
                    .persistDate(userLocalDate)
                    .user(users[(int) (Math.random() * numOfUsers)])
                    .build();
            mediaCommentService.create(MediaComment.builder()
                    .comment(mediaComments[i])
                    .media(medias[(int) (Math.random() * numOfMedias)])
                    .build());
        }
    }

    private void createAudiosEntity() {
        for (int i = 0; i != numOfMedias; i++) {
            if (i % 5 == 0) {
                audiosService.create(Audios.builder()
                        .author("Test Author " + i)
                        .icon("TestIcon" + i)
                        .name("AudioTestName " + i)
                        .media(medias[i])
                        .build());
            }
        }
    }

    private void createImageEntity() {
        for (int i = 0; i != numOfMedias; i++) {
            if (i % 5 == 0) {
                continue;
            } else if (i % 3 == 0) {
                continue;
            } else {
                imageService.create(Image.builder()
                        .description("Image Media Test File " + i)
                        .media(medias[i])
                        .build());
            }
        }
    }

    private void createVideosEntity() {
        for (int i = 0; i != numOfMedias; i++) {
            if (i % 5 == 0) {
                continue;
            } else if (i % 3 == 0) {
                videosService.create(Videos.builder()
                        .icon("New Test Icon video " + i)
                        .name("Test video " + i)
                        .media(medias[i])
                        .build());
            }
        }
    }

    private void createPostMessageUserEntity() {
        int postNum = 0;
        int messageNum = 0;
        for (int i = 0; i != numOfUsers / 2; i++) {
            Set<Post> postSet = new HashSet<>();
            postSet.add(posts[postNum]);
            postSet.add(posts[postNum++]);
            postNum++;
            Set<Message> messageSet = new HashSet<>();
            messageSet.add(messages[messageNum]);
            messageSet.add(messages[messageNum++]);
            messageNum++;
            users[i].setPosts(postSet);
            users[i].setMessages(messageSet);
            userService.update(users[i]);
        }
    }


}
