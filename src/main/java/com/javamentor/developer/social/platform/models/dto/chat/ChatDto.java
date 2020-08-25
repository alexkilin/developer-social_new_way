package com.javamentor.developer.social.platform.models.dto.chat;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDto {
    @ApiModelProperty(notes = "Id чата.")
    private Long id;
    @ApiModelProperty(notes = "Тип чата.")
    private String type;
    @ApiModelProperty(notes = "Название чата - groupChats или singleChats.")
    private String title;
    @ApiModelProperty(notes = "Фотография чата.")
    private String image;
    @ApiModelProperty(notes = "Последнее сообщение.")
    private String lastMessage;
    @ApiModelProperty(notes = "Пользователь в сети или не в сети.")
    private String active;
}
