package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.UserFriendDto;
import com.javamentor.developer.social.platform.models.entity.user.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
@Service
public abstract class UserFriendsConverter {

    @Mappings({
            @Mapping(target = "id", source = "friend.userId"),
            @Mapping(target = "fullName", source = "friend.firstName"),
            @Mapping(target = "avatar", source = "friend.avatar"),
            @Mapping(target = "education", source = "friend.education"),
            @Mapping(target = "profession", source = "friend.profession"),
            @Mapping(target = "status", source = "friend.status")
    })
    abstract UserFriendDto toDto(Friend friend);

    public abstract List<UserFriendDto> toDto(List<Friend> friend);
}
