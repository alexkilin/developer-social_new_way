package com.javamentor.developer.social.platform.models.dto.chat;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MediaDto {
    @ApiModelProperty(notes = "Ссылка на медиа.")
    private String url;
}
