package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.PostDAO;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.media.MediaService;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PostServiceImpl extends GenericServiceAbstract<Post, Long> implements PostService {


    private final UserService userService;
    private final MediaService mediaService;


    @Autowired
    public PostServiceImpl(PostDAO dao, UserService userService, MediaService mediaService) {
        super(dao);
        this.userService = userService;
        this.mediaService = mediaService;
    }

    @Override
    public void create(Post entity) {
        Set<Media> mediaSet = new HashSet<>();
        if (entity.getMedia() != null) {
            for (Media media : entity.getMedia()) {
                User user = userService.getById(media.getUser().getUserId());
                media.setUser(user);
                mediaSet.add(media);
                mediaService.create(media);
            }
        }
        User user = userService.getById(entity.getUser().getUserId());
        entity.setUser(user);
        entity.setMedia(mediaSet);
        super.create(entity);
    }


}
