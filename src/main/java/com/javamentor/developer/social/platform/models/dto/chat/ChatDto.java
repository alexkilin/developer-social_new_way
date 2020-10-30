package com.javamentor.developer.social.platform.models.dto.chat;

import com.javamentor.developer.social.platform.models.util.OnCreate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDto {
    @ApiModelProperty(notes = "Id чата.")
    @Null(groups = OnCreate.class, message = "Поле id должно принимать null значение при создании")
    private Long id;
    @ApiModelProperty(notes = "Тип чата.")
    private String type;
    @ApiModelProperty(notes = "Название чата - groupChats или singleChats.")
    @NotNull(groups = OnCreate.class, message = "Поле 'тема' не должно быть Null при создании")
    private String title;
    @ApiModelProperty(notes = "Фотография чата.")
    @NotNull(groups = OnCreate.class, message = "Поле 'картинка' не должно быть Null при создании")
    private String image;
    @ApiModelProperty(notes = "Последнее сообщение.")
    private String lastMessage;
    @ApiModelProperty(notes = "Пользователь в сети или не в сети.")
    private String active;
}
