package com.javamentor.developer.social.platform.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class PostDTO {

    @ApiModelProperty(notes = "Автоматически генерируемыЙ ID новости. При создании не указывать, указывать при изменении")
    private int id;

    @ApiModelProperty(notes = "Заголовок новости, поле не должно быть пустым")
    private String title;

    @ApiModelProperty(notes = "Текст новсти, поле не должно быть пустым")
    private String text;

}
