package com.javamentor.developer.social.platform.models.dto.media.image;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageDto {

    @ApiModelProperty(notes = "Id изображения")
    private Long id;

    @ApiModelProperty(notes = "Url изображения")
    private String url;

    @ApiModelProperty(notes = "Описание")
    private String description;

    @ApiModelProperty(notes = "Дата создания", example = "2020-09-14 19:08:50.967047")
    private LocalDateTime persistDateTime;

}