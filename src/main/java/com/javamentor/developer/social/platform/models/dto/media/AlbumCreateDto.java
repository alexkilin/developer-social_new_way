package com.javamentor.developer.social.platform.models.dto.media;

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
public class AlbumCreateDto {

    @ApiModelProperty(value = "Id владельца альбома", example = "60")
    @NotNull(groups = OnCreate.class, message = "User Id cant be null")
    private Long userId;

    @ApiModelProperty(value = "Имя альбома", example = "Котики / Высоцкий - Лучшие песни / Видосики с отпуска")
    @NotBlank(groups = OnCreate.class, message = "Name cant be empty")
    private String name;

    @ApiModelProperty(value = "Путь к файлу обложки", example = "/stuff/alb3.jpg")
    @NotBlank(groups = OnCreate.class, message = "Icon path cant be empty")
    private String icon;
}
