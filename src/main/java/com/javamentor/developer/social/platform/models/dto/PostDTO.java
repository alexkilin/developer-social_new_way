package com.javamentor.developer.social.platform.models.dto;

import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class PostDTO {

    @ApiModelProperty(notes = "Автоматически генерируемыЙ ID новости. При создании не указывать, указывать при изменении")
    private int id;

    @NotNull
    @ApiModelProperty(notes = "Заголовок новости, поле не должно быть пустым")
    private String title;

    @NotNull
    @ApiModelProperty(notes = "Текст новсти, поле не должно быть пустым")
    private String text;

    @ApiModelProperty(notes = "Пользователь, добавивший пост")
    private User user;

    @ApiModelProperty(notes = "Дата последнего изменения")
    private LocalDateTime lastRedactionDate;

    @ApiModelProperty(notes = "Дата добавления поста")
    private LocalDateTime persistDate;

    @ApiModelProperty(notes = "Медиаконтент поста")
    private Set<Media> media;
}
