package com.javamentor.developer.social.platform.models.dto;

import com.javamentor.developer.social.platform.models.util.OnCreate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistCreateDto {


    @NotBlank(groups = OnCreate.class, message = "Name can't be blank")
    @ApiModelProperty(notes = "Имя плейлиста")
    private String name;

    @NotBlank(groups = OnCreate.class, message = "Image name can't be blank")
    @ApiModelProperty(notes = "Изображение для плейлиста")
    private String image;

    @Null(groups = OnCreate.class)
    @ApiModelProperty(notes = "ID пользователя владельца", hidden = true)
    private Long ownerUserId;
}
