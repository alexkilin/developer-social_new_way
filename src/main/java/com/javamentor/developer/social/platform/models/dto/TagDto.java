package com.javamentor.developer.social.platform.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class TagDto {

    @ApiModelProperty(notes = "Автоматически генерируемыЙ ID Тэга. При создании не указывать, указывать при изменении")
    private Long id;

    @ApiModelProperty(notes = "Текст тэга")
    private String text;
}
