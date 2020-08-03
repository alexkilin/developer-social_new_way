package com.javamentor.developer.social.platform.webapp.converters;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFriendsConverter {

    public final List<FriendDto> toDto(List<Friend> friendList) {

        List<FriendDto> friendDtoList = new ArrayList<FriendDto>();

        for (Friend friend : friendList) {
            friendDtoList.add(
                    new FriendDto(friend.getId(),
                                  friend.user.getId(),
                                  friend.friend.getId())
            );
        }

        return friendDtoList;
    }

}
