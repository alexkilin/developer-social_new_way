package com.javamentor.developer.social.platform.service.abstracts.model.post;

import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.post.UserTabs;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

public interface UserTabsService extends GenericService<UserTabs, Long> {
    void deletePost(Post post);
}
