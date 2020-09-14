package com.javamentor.developer.social.platform.models.dto.chat;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    @ApiModelProperty(notes = "Последнее редактирование.", hidden = true)
    private LocalDateTime lastRedactionDate;
    @ApiModelProperty(notes = "Дата публикования сообщения.", hidden = true)
    private LocalDateTime persistDate;
    @ApiModelProperty(notes = "Все медиа.")
    private List<MediaDto> mediaDto;
    @ApiModelProperty(notes = "Аватар отправителя.")
    private String userSenderImage;
    @ApiModelProperty(notes = "Текст сообщения.")
    private String message;
    @ApiModelProperty(notes = "Пользователь в сети или не в сети.")
    private String active;
}
