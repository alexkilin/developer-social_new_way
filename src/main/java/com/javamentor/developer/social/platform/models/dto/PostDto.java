package com.javamentor.developer.social.platform.models.dto;

import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class PostDto {

    @ApiModelProperty(notes = "Автоматически генерируемыЙ ID новости. При создании не указывать, указывать при изменении", example = "1")
    @Null(groups = OnCreate.class, message = "Поле id должно принимать null значение при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле id не должно принимать null значение при обновлении")
    private Long id;

    @NotNull
    @ApiModelProperty(notes = "Заголовок новости, поле не должно быть пустым")
    private String title;

    @NotNull
    @ApiModelProperty(notes = "Текст новости, поле не должно быть пустым")
    private String text;

    @ApiModelProperty(notes = "Айди пользователя, добавившего пост", example = "1")
    private Long userId;

    @ApiModelProperty(notes = "Имя пользователя")
    private String firstName;

    @ApiModelProperty(notes = "Фамилия пользователя")
    private String lastName;

    @ApiModelProperty(notes = "Ссылка на аватарку пользователя")
    private String avatar;

    @ApiModelProperty(notes = "Дата последнего изменения")
    private LocalDateTime lastRedactionDate;

    @ApiModelProperty(notes = "Дата добавления поста")
    private LocalDateTime persistDate;

    @ApiModelProperty(notes = "Медиаконтент поста")
    private List<MediaPostDto> media;

    @ApiModelProperty(notes = "Тэги новости")
    private List<TagDto> tags;
}
