package com.javamentor.developer.social.platform.models.dto.comment;

import com.javamentor.developer.social.platform.models.entity.comment.CommentType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class CommentDto {

    private Long id;

    private String comment;

    private CommentType commentType;

    private LocalDateTime lastRedactionDate;

    private LocalDateTime persistDate;

    private User user;
}
