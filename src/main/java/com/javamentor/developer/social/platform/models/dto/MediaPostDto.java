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
public class MediaPostDto {

    @ApiModelProperty(notes = "Вид медиаконтента")
    private String mediaType;

    @ApiModelProperty(notes = "Ссылка на контент")
    private String content;
}
