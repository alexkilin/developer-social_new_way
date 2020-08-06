package com.javamentor.developer.social.platform.dao.abstracts.model;

import com.javamentor.developer.social.platform.models.entity.user.Friend;

import java.util.List;

public interface UserFriendsDao {

    List<Friend> getUserFriendsById(Long id);
}
