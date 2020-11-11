package com.javamentor.developer.social.platform.models.dto.media.music;

import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistGetDto {

    @ApiModelProperty(notes = "ID плейлиста", example = "3")
    private Long id;

    @ApiModelProperty(notes = "Имя плейлиста", example = "Утренняя тренировка")
    private String name;

    @ApiModelProperty(notes = "Изображение для плейлиста", example = "image_12")
    private String image;

    @ApiModelProperty(notes = "ID пользователя владельца", example = "60")
    private Long ownerUserId;

    @ApiModelProperty(notes = "Содержимое плейлиста, список")
    private List<AudioDto> content;

    @ApiModelProperty(notes = "Дата создания", example = "2020-09-14 19:08:50.967047")
    private LocalDateTime persistDateTime;
}