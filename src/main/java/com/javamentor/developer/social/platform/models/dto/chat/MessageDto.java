package com.javamentor.developer.social.platform.models.dto.chat;

import com.javamentor.developer.social.platform.models.util.OnCreate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MessageDto {
    @ApiModelProperty(notes = "Id сообщения.")
    @Null(groups = OnCreate.class, message = "Поле id должно принимать null значение при создании")
    private Long id;
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
