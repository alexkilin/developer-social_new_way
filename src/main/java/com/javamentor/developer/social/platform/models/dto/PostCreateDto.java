package com.javamentor.developer.social.platform.models.dto;

import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class PostCreateDto {

    @ApiModelProperty(notes = "Автоматически генерируемыЙ ID новости. При создании не указывать, указывать при изменении", example = "null")
    @Null(groups = OnCreate.class, message = "Поле id должно принимать null значение при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле id не должно принимать null значение при обновлении")
    private Long id;

    @NotNull
    @Size(groups = OnCreate.class, max = 50, message = "Значение поля title не может превышать 50 символов")
    @ApiModelProperty(notes = "Заголовок новости, поле не должно быть пустым")
    private String title;

    @NotNull
    @Size(groups = OnCreate.class, max = 1000, message = "Значение поля text не может превышать 1000 символов")
    @ApiModelProperty(notes = "Текст новости, поле не должно быть пустым")
    private String text;

    @ApiModelProperty(notes = "ID пользователя, добавившего пост, обязательное поле", example = "1")
    private Long userId;

    @ApiModelProperty(notes = "Медиаконтент поста")
    @Valid
    private List<MediaPostDto> media;

    @ApiModelProperty(notes = "Тэги новости")
    @Valid
    private List<TagDto> tags;

    @NotNull
    @ApiModelProperty(notes = "Тема топика, не может быть нулевой")
    @Valid
    private TopicDto topic;
}
