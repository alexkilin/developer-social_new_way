package com.javamentor.developer.social.platform.service.abstracts.model.user;

import com.javamentor.developer.social.platform.models.entity.user.Friend;
import java.util.List;

public interface UserFriendsService {

    List<Friend> getUserFriendsById(Long id);

}
