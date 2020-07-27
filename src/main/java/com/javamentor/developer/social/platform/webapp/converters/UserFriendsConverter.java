package com.javamentor.developer.social.platform.webapp.converters;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFriendsConverter {

    public final List<FriendDto> convertToDto(List<Friend> friendList) {

        List<FriendDto> friendDtoList = new ArrayList<FriendDto>();

        for (Friend friend : friendList) {
            friendDtoList.add(
                    new FriendDto(friend.getId())
            );
        }

        return friendDtoList;
    }

}
