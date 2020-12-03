package com.javamentor.developer.social.platform.models.dto.chat;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MediaDto {
    @ApiModelProperty(notes = "Id медиа.")
    private Long id;
    @ApiModelProperty(notes = "Ссылка на медиа.")
    private String url;
    @ApiModelProperty(notes = "Тип медиа.")
    private String mediaType;
    @ApiModelProperty(notes = "Дата публикования медиа.", hidden = true)
    private LocalDateTime persistDateTime;
    @ApiModelProperty(notes = "Id сообщения")
    private Long messageId;
}
