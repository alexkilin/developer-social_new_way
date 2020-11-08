package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.PostDao;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.post.Tag;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.media.MediaService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.TagService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl extends GenericServiceAbstract<Post, Long> implements PostService {


    private final UserService userService;
    private final MediaService mediaService;
    private final TagService tagService;


    @Autowired
    public PostServiceImpl(PostDao dao, UserService userService, MediaService mediaService, TagService tagService) {
        super(dao);
        this.userService = userService;
        this.mediaService = mediaService;
        this.tagService = tagService;
    }

    @Override
    public void create(Post entity) {

        Set<Media> mediaSet = createMedia(entity);
        createTag(entity);

        Optional<User> userOptional = userService.getById(entity.getUser().getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            entity.setUser(user);
            entity.setMedia(mediaSet);
            super.create(entity);
        }
    }

    private Set<Media> createMedia(Post entity) {
        Set<Media> entityMedia = entity.getMedia();

        if (Objects.nonNull(entityMedia)) {

            List<Long> ids = entityMedia.stream()
                    .map(Media::getUser)
                    .map(User::getUserId)
                    .collect(Collectors.toList());

            Optional<List<User>> optionalUsers = userService.getUsers(ids);

            if (optionalUsers.isPresent()) {
                List<User> users = optionalUsers.get();
                int i = 0;
                for (Media media : entityMedia) {
                    media.setUser(users.get(i++));
                    mediaService.create(media);
                }
            }
        }
        return new HashSet<>(entityMedia);
    }

    private void createTag(Post entity) {
        Set<Tag> entityTags = entity.getTags();
        Set<Tag> tagSet = new HashSet<>();

        if (Objects.nonNull(entityTags)) {

            List<String> textOfTags = entityTags.stream()
                    .map(Tag::getText)
                    .collect(Collectors.toList());

            Optional<List<Tag>> optionalTags = tagService.getTagsByText(textOfTags);

            if (optionalTags.isPresent()) {
                List<Tag> tags = optionalTags.get();
                for (Tag eTag : entityTags) {
                    for (Tag oTag : tags) {
                        if (eTag.getText().equals(oTag.getText())) {
                            eTag.setId(oTag.getId());
                        }
                    }
                }
            }

            for (Tag tag : entityTags) {
                if (Objects.isNull(tag.getId())) {
                    tagService.create(tag);
                }
                tagSet.add(tag);
            }
            entity.setTags(tagSet);
        }
    }
}

