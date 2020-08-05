package com.javamentor.developer.social.platform.models.dto.group;

import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class GroupWallDto {

    @ApiModelProperty(notes = "Автоматически генерируемыЙ ID поста. При создании не указывать, указывать при изменении")
    private Long id;

    @NonNull
    @ApiModelProperty(notes = "Заголовок поста, поле не должно быть пустым")
    private String title;

    @NonNull
    @ApiModelProperty(notes = "Текст поста, поле не должно быть пустым")
    private String text;

    @NonNull
    @ApiModelProperty(notes = "Дата последнего изменения, поле не должно быть пустым")
    private LocalDateTime lastRedactionDate;

    @NonNull
    @ApiModelProperty(notes = "Дата добавления поста, поле не должно быть пустым")
    private LocalDateTime persistDate;

    @ApiModelProperty(notes = "Кол-во комментариев в посте")
    private Long countComments;

    @ApiModelProperty(notes = "Кол-во лайков у поста")
    private Long countLikes;

    @ApiModelProperty(notes = "Медиаконтент поста")
    private List<MediaPostDto> media;

    @ApiModelProperty(notes = "Тэги поста")
    private List<TagDto> tags;
}
