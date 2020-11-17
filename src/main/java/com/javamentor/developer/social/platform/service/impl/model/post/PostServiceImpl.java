package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.PostDao;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.post.Tag;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.media.MediaService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.TagService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl extends GenericServiceAbstract<Post, Long> implements PostService {


    private final MediaService mediaService;
    private final TagService tagService;


    @Autowired
    public PostServiceImpl(PostDao dao, MediaService mediaService, TagService tagService) {
        super(dao);
        this.mediaService = mediaService;
        this.tagService = tagService;
    }

    @Override
    @Transactional
    public void create(Post entity) {
        Set<Media> mediaSet = createMedia(entity.getMedia(), entity.getUser());
        Set<Tag> tagSet = updateAndGetEntityTags(entity.getTags());
        entity.setMedia(mediaSet);
        entity.setTags(tagSet);
        super.create(entity);
    }

    private Set<Media> createMedia(Set<Media> entityMedia, User user) {
        if (Objects.nonNull(entityMedia) && !entityMedia.isEmpty()) {
            for (Media media : entityMedia) {
                media.setUser(user);
                mediaService.create(media);
            }
        }
        return new HashSet<>(entityMedia);
    }

    private Set<Tag> updateAndGetEntityTags(Set<Tag> entityTags) {
        Set<Tag> tagSet = new HashSet<>();

        if (Objects.nonNull(entityTags) && !entityTags.isEmpty()) {
            List<String> textOfTags = entityTags.stream()
                    .map(Tag::getText)
                    .collect(Collectors.toList());

            List<Tag> tagList = tagService.getTagsByText(textOfTags);

            searchAndAddTagId(entityTags, tagList);

            createTag(entityTags, tagSet);

        }
        return tagSet;
    }

    private void createTag(Set<Tag> entityTags, Set<Tag> tagSet) {
        for (Tag tag : entityTags) {
            if (Objects.isNull(tag.getId())) {
                tagService.create(tag);
            }
            tagSet.add(tag);
        }
    }

    private void searchAndAddTagId(Set<Tag> entityTags, List<Tag> tagList) {
        if (Objects.nonNull(tagList) && !tagList.isEmpty()) {
            for (Tag eTag : entityTags) {
                for (Tag tList : tagList) {
                    if (eTag.getText().equals(tList.getText())) {
                        eTag.setId(tList.getId());
                    }
                }
            }
        }
    }

}

