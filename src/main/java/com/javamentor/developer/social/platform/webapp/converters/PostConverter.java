package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostCreateDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class PostConverter {

    @Mappings({
            @Mapping(target = "media", source = "media"),
            @Mapping(target = "user.userId", source = "userId"),

    })
    public abstract Post toEntity(PostDto postDto);

    @Mappings({
            @Mapping(target = "media", source = "media"),
            @Mapping(target = "userId", source = "user.userId"),
            @Mapping(target = "firstName", source = "user.firstName"),
            @Mapping(target = "lastName", source = "user.lastName"),
            @Mapping(target = "avatar", source = "user.avatar")
    })
    public abstract PostDto toDto(Post post);

    @Mappings({
            @Mapping(target = "user.userId",source = "userId")
    })
    public abstract Media toEntityMedia(MediaPostDto mediaPostDto);

    @Mappings({
            @Mapping(target = "userId",source = "user.userId")
    })
    public abstract MediaPostDto toMediaPostDto(Media media);

    @Mappings({
            @Mapping(target = "media", source = "media"),
            @Mapping(target = "user.userId", source = "userId"),
    })
    public abstract Post toEntity(PostCreateDto postCreateDto);

}
