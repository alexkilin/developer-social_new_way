package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public abstract class PostConverter {

    @Mappings({
            @Mapping(target = "user", source = "userDto")
    })
    public abstract Post toEntity(PostDto postDto);

    @Mappings({
            @Mapping(target = "userDto", source = "user")
    })
    public abstract PostDto toDto(Post post);

}
