package com.javamentor.developer.social.platform.models.dto;

import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistCreateDto {


    @NotBlank(message = "Name can't be blank")
    @ApiModelProperty(notes = "Имя плейлиста")
    private String name;

    @NotBlank(message = "Image name can't be blank")
    @ApiModelProperty(notes = "Изображение для плейлиста")
    private String image;

    @Null
    @ApiModelProperty(notes = "ID пользователя владельца", hidden = true)
    private Long ownerUserId;
}
