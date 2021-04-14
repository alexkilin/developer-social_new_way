package com.javamentor.developer.social.platform.models.dto.media.music;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GenreDto {

    @ApiModelProperty(notes = "Id жанра. Генерируется автоматически.")
    private Long id;

    @ApiModelProperty(notes = "Название жанра")
    private String title;

}