package com.javamentor.developer.social.platform.models.dto.comment;

import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(notes = "Уникальный идентификационный номер комментария. Генерируется автоматически.")
    private Long id;

    @ApiModelProperty(notes = "Текстовая информация в комментарии.")
    private String comment;

    @ApiModelProperty(notes = "Время последней редакции комментария.")
    private LocalDateTime lastRedactionDate;

    @ApiModelProperty(notes = "Время создания комментария.")
    private LocalDateTime persistDate;

    @ApiModelProperty(notes = "ФИО пользователя оставившего комментарий.")
    private UserDto userDto;
}
