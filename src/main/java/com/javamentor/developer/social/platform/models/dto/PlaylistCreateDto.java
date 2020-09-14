package com.javamentor.developer.social.platform.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistCreateDto {

    @ApiModelProperty(notes = "Имя плейлиста")
    private String name;

    @ApiModelProperty(notes = "Изображение для плейлиста")
    private String image;

    @ApiModelProperty(notes = "ID пользователя владельца")
    private Long ownerUserId;
}
