package com.javamentor.developer.social.platform.dao.abstracts.model;

import java.util.List;

public interface UserFriendsDao {

    List<Friend> getUserFriendsById(Long id);
}
