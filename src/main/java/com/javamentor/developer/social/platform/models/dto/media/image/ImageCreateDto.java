package com.javamentor.developer.social.platform.models.dto.media.image;

import com.javamentor.developer.social.platform.models.util.OnCreate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageCreateDto {

    @NotBlank(groups = OnCreate.class, message = "Url can't be blank")
    @ApiModelProperty(notes = "Url изображения")
    private String url;

    @NotNull(groups = OnCreate.class, message = "Description can't be null")
    @ApiModelProperty(notes = "Описание изображения", example = "Я и моя сраная кошка")
    private String description;

    @NotNull(groups = OnCreate.class, message = "User Id cant be null")
    @ApiModelProperty(notes = "ID пользователя, добавившего изображение", example = "60")
    private Long userId;

}