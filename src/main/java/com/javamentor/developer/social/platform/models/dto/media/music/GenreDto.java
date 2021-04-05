package com.javamentor.developer.social.platform.models.dto.media.music;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GenreDto {

    @ApiModelProperty(notes = "Id жанра")
    private Long id;

    @ApiModelProperty(notes = "Название жанра")
    private String title;

}