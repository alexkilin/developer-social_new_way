package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class PostCommentConverter {

    @Mappings({
            @Mapping(source = "commentDto.comment", target = "comment.comment"),
            @Mapping(source = "commentDto.lastRedactionDate", target = "comment.lastRedactionDate"),
            @Mapping(source = "commentDto.persistDate", target = "comment.persistDate"),
            @Mapping(source = "commentDto.userDto", target = "comment.user"),
            @Mapping(source = "postId", target = "post.id"),
    })
    public abstract PostComment toPostCommentEntity(CommentDto commentDto, Long postId);
}
